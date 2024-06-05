package com.portfolio.www.auth;

import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.portfolio.www.message.MessageEnum;
import com.portfolio.www.service.LoginService;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Controller
public class LoginController {

	@Autowired
	LoginService loginService;
	
	// 인증메일 인증 후 비밀번호 변경
	@RequestMapping("emailAuthPw.do")
	public ModelAndView emailAuthPw(@RequestParam String authNum,
			@RequestParam String email,
			Model model) {
		System.out.println("==========================emailAuthPw controller진입========================");
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		
		HashMap<String,String> emailAuthPw = new HashMap<>();
		emailAuthPw.put("authNum", String.valueOf(authNum));
		emailAuthPw.put("email", email);
		
		// 1(성공) 또는 0(실패)를 반환함
		int cnt = loginService.emailAuthPw(emailAuthPw);
		//param으로 받은 번호화 db에 저장된 인증번호 비교 후 같으면 비밀번호 변경
		if(cnt == 1) {
			mv.setViewName("auth/passwordChange");
			model.addAttribute("email",email);
			
			return mv;
		}
		
		return mv;
	}

	// 비밀번호 변경 인증번호 생성 및 인증메일 발송
	@RequestMapping("/auth/convertPw.do")
	public ModelAndView convertPw(@RequestParam String email,
			Model model) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		mv.setViewName("auth/login");
		System.out.println("====================convertPw controller 진입====================");
		// 6자리의 인증번호 생성
		int authNum = (int) (Math.random() * (999999 - 100000 + 1)) + 100000;
		
		HashMap<String,String> updateAuthNum = new HashMap<>();
		
		updateAuthNum.put("authNum", String.valueOf(authNum));
		updateAuthNum.put("email", email);
		
		loginService.updateAuthNum(updateAuthNum);
		// 빨간글씨로 띄울거 알람보냄
		model.addAttribute("alert","이메일을 확인하세요.");
		return mv;
	}

	// 비밀번호 변경페이지로 이동 & 비밀번호 변경
	@RequestMapping("/auth/resetPw.do")
	public ModelAndView resetPw(@RequestParam HashMap<String, String> params,
			Model model) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		
		if(!params.isEmpty()) {
			mv.setViewName("auth/login");
			if(isValidPassword(params.get("passwd"))) {
				loginService.changePasswd(params);
				return mv;
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
	public String login(@RequestParam HashMap<String, String> params, HttpServletRequest request, Model model) {

		// 아이디로 비밀번호 찾기
		String dbPasswd = loginService.login(params.get("memberID"));

		String passwd = params.get("passwd");

		BCrypt.Result result = BCrypt.verifyer().verify(passwd.toCharArray(), dbPasswd);

		// System.out.println("result=====비밀번호확인========>"+result);

		if (result.validFormat && result.verified) {
			// 비밀번호가 일치하는 경우
			// 로그인 성공 처리
			HttpSession session = request.getSession();
			session.setAttribute("logInUser", params.get("memberID"));
			return "redirect:/index.do";
		} else {
			// 비밀번호가 일치하지 않는 경우
			// 로그인 실패 처리
			model.addAttribute("errorMessage", MessageEnum.LOGIN_FAILD.getDescription());
			return "auth/login";
		}
	}

	@RequestMapping("/auth/loginPage.do")
	public ModelAndView loginPage(@RequestParam HashMap<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		mv.setViewName("auth/login");

		return mv;
	}
	
	// 비밀번호 제약사항 - 비밀번호는 8~16자의 영문, 숫자, 특수문자를 1개 이상 포함해야 함.
	private boolean isValidPassword(String password) {
	    return password != null && password.length() >= 8 && password.length() <= 16 &&
	           password.matches(".*[a-z].*") && password.matches(".*\\d.*") &&
	           password.matches(".*[!@#$%^&*()\\-_=+\\[\\]{}|;:'\",.<>?/].*");
	}
}
