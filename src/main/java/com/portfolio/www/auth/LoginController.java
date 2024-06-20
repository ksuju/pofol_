package com.portfolio.www.auth;

import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.portfolio.www.message.MessageEnum;
import com.portfolio.www.service.LoginService;


@Controller
public class LoginController {

	@Autowired
	LoginService loginService;

	// 인증메일 인증 후 비밀번호 변경
	@RequestMapping("emailAuthPw.do")
	public ModelAndView emailAuthPw(@RequestParam String authNum, @RequestParam String email, Model model) {
		System.out.println("==========================emailAuthPw controller진입========================");
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());

		HashMap<String, String> emailAuthPw = new HashMap<>();
		emailAuthPw.put("authNum", String.valueOf(authNum));
		emailAuthPw.put("email", email);

		// 1(성공) 또는 0(실패)를 반환함
		int cnt = loginService.emailAuthPw(emailAuthPw);
		// param으로 받은 번호화 db에 저장된 인증번호 비교 후 같으면 비밀번호 변경
		if (cnt == 1) {
			mv.setViewName("auth/passwordChange");
			model.addAttribute("email", email);

			return mv;
		}
		return mv;
	}

	// 비밀번호 변경 인증번호 생성 및 인증메일 발송
	@RequestMapping("/auth/convertPw.do")
	public ModelAndView convertPw(@RequestParam String email, Model model) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		System.out.println("====================convertPw controller 진입====================");
		// 6자리의 인증번호 생성
		int authNum = (int) (Math.random() * (999999 - 100000 + 1)) + 100000;

		HashMap<String, String> updateAuthNum = new HashMap<>();

		updateAuthNum.put("authNum", String.valueOf(authNum));
		updateAuthNum.put("email", email);

		int cnt = loginService.updateAuthNum(updateAuthNum);

		if (cnt == 1) {
			mv.setViewName("auth/login");
			// 빨간글씨로 띄울거 알람보냄
			model.addAttribute("alert", "이메일을 확인하세요.");
			return mv;
		} else if (cnt == Integer.parseInt(MessageEnum.VALLID_EMAIL.getCode())) {
			mv.setViewName("auth/recoverPassword");
			// 빨간글씨로 띄울거 알람보냄
			model.addAttribute("alert", MessageEnum.VALLID_EMAIL.getDescription());
			return mv;
		} else {
			mv.setViewName("auth/recoverPassword");
			// 빨간글씨로 띄울거 알람보냄
			model.addAttribute("alert", "이메일 인증 실패");
			return mv;
		}

	}

	// 비밀번호 변경페이지로 이동 & 비밀번호 변경
	@RequestMapping("/auth/resetPw.do")
	public ModelAndView resetPw(@RequestParam HashMap<String, String> params, Model model) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());

		if (!params.isEmpty()) {
			mv.setViewName("auth/login");
			if (isValidPassword(params.get("passwd"))) {
				int changePW = loginService.changePasswd(params);
				if (changePW == 1) {
					return mv;
				} else if (changePW == Integer.parseInt(MessageEnum.EXPIRE_AUTH_DTM.getCode())) {
					mv.setViewName("auth/login");
					model.addAttribute("alert", MessageEnum.EXPIRE_AUTH_DTM.getDescription());
					return mv;
				}
			}
			mv.setViewName("auth/passwordChange");
			model.addAttribute("email", params.get("email"));
			model.addAttribute("alert", MessageEnum.VALLID_PASSWD.getDescription());
			return mv;
		}

		mv.setViewName("auth/recoverPassword");
		return mv;
	}

	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest request) {

		System.out.println("==================logout진입==================");
		// 세션 무효화
		request.getSession().invalidate();

		return "redirect:/index.do";
	}

	@RequestMapping("/auth/login.do")
	public String login(@RequestParam HashMap<String, String> params
			, HttpServletRequest request
			, HttpServletResponse response
			, Model model) {
		
		System.out.println("======== LoginController > login.do ========");
		
		String memberId = params.get("memberID");
		
		// 아이디저장 클릭시 쿠키 생성
		loginService.saveIdCookie(params, response);
		
		// 아이디로 비밀번호 찾기
		int loginCheak = loginService.loginCheak(memberId, params.get("passwd"), request);
		
		if (loginCheak == Integer.parseInt(MessageEnum.SUCCESS.getCode())) {
			// 비밀번호가 일치하는 경우
			// 로그인 성공 처리
			return "redirect:/index.do";
		} else if (loginCheak == Integer.parseInt(MessageEnum.NOT_EMAIL_AUTH.getCode())) {
			// 이메일 인증이 완료되지 않은 계정인 경우
			// 로그인 실패 처리
			System.out.println("로그인실패 > 이메일인증안됨==========================");
			model.addAttribute("errorMessage", MessageEnum.NOT_EMAIL_AUTH.getDescription());
			// 아이디저장 쿠키가 존재하면 request에 saveId라는 이름으로 저장됨
			existCookie(request);
	        // request에 설정된 속성을 ModelAndView에 추가
	        String saveId = (String) request.getAttribute("saveId");
	        model.addAttribute("saveId",saveId);
			return "auth/login";
		}
		// 비밀번호가 일치하지 않는 경우
		// 로그인 실패 처리
		System.out.println("로그인실패==========================");
		// 쿠키삭제
		deleteCookie(request);
		model.addAttribute("errorMessage", MessageEnum.LOGIN_FAILD.getDescription());
		return "auth/login";
	}

	@RequestMapping("/auth/loginPage.do")
	public ModelAndView loginPage(@RequestParam HashMap<String, String> params
			, HttpServletRequest request) {
		System.out.println("========================== LoginController > loginPage ========================");
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		
		// 아이디저장 쿠키가 존재하면 request에 saveId라는 이름으로 저장됨
		existCookie(request);
        // request에 설정된 속성을 ModelAndView에 추가
        String saveId = (String) request.getAttribute("saveId");
        mv.addObject("saveId", saveId);
        
		mv.setViewName("auth/login");
		return mv;
	}

	// 비밀번호 제약사항 - 비밀번호는 8~16자의 영문, 숫자, 특수문자를 1개 이상 포함해야 함.
	private boolean isValidPassword(String password) {
		return password != null && password.length() >= 8 && password.length() <= 16 && password.matches(".*[a-z].*")
				&& password.matches(".*\\d.*") && password.matches(".*[!@#$%^&*()\\-_=+\\[\\]{}|;:'\",.<>?/].*");
	}
	
	
	// 쿠키에 저장된 아이디가 있을 때 request에 saveId라는 이름으로 저장
	private void existCookie(HttpServletRequest request) {
		String memberId = "";
		
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("saveId")) {
            	memberId = cookie.getValue();
            	break; // 쿠키를 찾았으니 더 이상 반복할 필요 없음
            }
        }
        request.setAttribute("saveId", memberId);
	}
	
	// 쿠키에 저장된 아이디 삭제
	private void deleteCookie(HttpServletRequest request) {
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if (cookie.getName().equals("saveId")) {
	                cookie.setMaxAge(0); // 쿠키의 유효 시간을 0으로 설정하여 삭제
	                cookie.setPath("/"); // 쿠키의 경로 설정 > 모든 페이지에서 삭제함
	                HttpServletResponse response = (HttpServletResponse) request.getAttribute("response");
	                try {
	                	response.addCookie(cookie); // 응답에 쿠키 추가
		                break;
	                } catch (NullPointerException ne) {
	                	System.out.println("============쿠키삭제 null============");
	                }
	            }
	        }
	    }
	}
}
