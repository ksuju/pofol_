package com.portfolio.www.auth;

import java.util.Calendar;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.portfolio.www.dto.EmailAuthDto;
import com.portfolio.www.message.MessageEnum;
import com.portfolio.www.service.JoinService;

@Controller
public class JoinController {
	
	@Autowired
	JoinService joinService;
	
	//joinPage 진입
	@RequestMapping("/auth/joinPage.do")
	public ModelAndView joinPage(@RequestParam HashMap<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		mv.setViewName("auth/join");
		
		System.out.println("==============================JoinController > joinPage.do진입==============================");
		return mv;
	}
	
	
	// 이메일 인증 체크
	@RequestMapping("emailAuth.do")
	public ModelAndView emailAuth(@RequestParam HashMap<String, String> params) {
		System.out.println("====================emailAuth진입===================="+params);
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		// params.value (authURI)와 db에서 불러온 authURI를 비교해야함
		if(joinService.authURI(params.get("uri")) != null) {
			EmailAuthDto emailAuthDto = joinService.authURI(params.get("uri"));
			
			// 트랜잭션으로 auth_yn 업데이트
            if(joinService.updateAuthAndMemAuth(emailAuthDto)) {
            	System.out.println("인증성공");
    			mv.setViewName("auth/login");
    			return mv;
            }
		}
		System.out.println("인증실패");
		mv.setViewName("index");
		return mv;
	}
	
	
	// 비밀번호 유효성 체크 기능
	@ResponseBody
	@RequestMapping("/auth/validPasswd.do")
	public int validPasswd(@RequestParam String passwordCheck) {
		System.out.println("==============================JoinController > passCheck.do진입==============================");
		// 비밀번호 제약사항 체크
		if(!isValidPassword(passwordCheck)) {
			return Integer.parseInt(MessageEnum.VALLID_PASSWD.getCode());
		}
		return 999999;
	}
	
	
	// 비밀번호 = 비밀번호확인 체크 기능
	@ResponseBody
	@RequestMapping("/auth/passCheck.do")
	public int passCheck(@RequestParam String conPassCheck,
			@RequestParam String passwordCheck) {
		System.out.println("==============================JoinController > passCheck.do진입==============================");
		// 비밀번호 제약사항 체크
		if(isValidPassword(passwordCheck)) {
			// 비밀번호 비교
			if(conPassCheck.equals(passwordCheck)) {
				System.out.println("controller > passCheck > 비밀번호 같음");
				System.out.println("==============================JoinController > passCheck.do종료==============================");
				return Integer.parseInt(MessageEnum.EQUAL_PASSWD.getCode());
			} else {
				System.out.println("controller > passCheck > 비밀번호 다름");
				System.out.println("==============================JoinController > passCheck.do종료==============================");
				return Integer.parseInt(MessageEnum.NOT_EQUAL_PASSWD.getCode());
			}
		} else {
			return Integer.parseInt(MessageEnum.VALLID_PASSWD.getCode());
		}

	}
	
	
	// 아이디 중복체크 기능
	@ResponseBody
	@RequestMapping("/auth/idCheck.do")
	public int idCheck(@RequestParam String idCheck) {
		System.out.println("==============================JoinController > idCheck.do진입==============================");
		int idCheckCode = joinService.idCheck(idCheck);
		
		// 유효한 아이디
		if(isValidUsername(idCheck)) {
			if(idCheckCode == Integer.parseInt(MessageEnum.DUPL_ID.getCode())) {
				System.out.println("controller > idCheck > 아이디중복");
				System.out.println("==============================JoinController > idCheck.do종료==============================");
				return idCheckCode;
			} else {
				System.out.println("controller > idCheck > 아이디중복없음");
				System.out.println("==============================JoinController > idCheck.do종료==============================");
				return idCheckCode;
			}
		} else {
			// 유효하지 않은 아이디
			return Integer.parseInt(MessageEnum.VALLID_USER_NAME.getCode());
		}
	}
	
	// 회원가입 기능
	@ResponseBody
	@RequestMapping("/auth/join.do")
	public String join(@RequestParam HashMap<String, String> params) {
		
		System.out.println("==============================JoinController > join.do진입==============================");
		
		if(params.isEmpty()) {
			return MessageEnum.FAILED.getCode();
		} else {
			// System.out.println("join params 확인 ========================>"+params);
			
			String username = params.get("memberID");
			String password = params.get("passwd");
			String confirmPassword = params.get("con_pass");
			
			// 아이디 유효성 검사
			if (!isValidUsername(username)) {
				System.out.println("유효하지 않은 아이디.");
				return MessageEnum.VALLID_USER_NAME.getCode();
			}
			
			// 비밀번호 유효성 검사
			if (!isValidPassword(password)) {
				System.out.println("유효하지 않은 패스워드.");
				return MessageEnum.VALLID_PASSWD.getCode();
			}
			
			// 비밀번호와 확인용 비밀번호가 일치하는지 확인
			if (!password.equals(confirmPassword)) {
				System.out.println("비밀번호가 일치하지 않습니다.");
				return MessageEnum.EQUAL_PASSWD.getCode();
			}
			
			// 아이디가 사용 가능한지 확인하는 조건
			int idCheckCode = joinService.idCheck(username);
			if (idCheckCode == Integer.parseInt(MessageEnum.DUPL_ID.getCode())) {
				System.out.println("중복된 아이디 입니다.");
				return MessageEnum.DUPL_ID.getCode();
			}
			
			// 모든 조건을 통과한 경우 회원가입 진행
			int joinCheck = joinService.joinMember(params);
			
			if (joinCheck == 1) {
				System.out.println("=====================회원가입 성공 확인=====================");
				System.out.println("==============================JoinController > join.do종료==============================");
				return MessageEnum.SUCCESS.getCode();
			} else {
				System.out.println("=====================회원가입 실패 확인=====================");
				System.out.println("==============================JoinController > join.do종료==============================");
				return MessageEnum.FAILED.getCode();
			}
		}
	}
	
	// 아이디 제약사항 - 아이디는 공백 또는 빈 칸일 수 없고 4~20자의 영어 소문자, 숫자만 사용 가능함.
	private boolean isValidUsername(String username) {
	    return username != null && username.matches("^[a-z0-9]{4,20}$");
	}

	// 비밀번호 제약사항 - 비밀번호는 8~16자의 영문, 숫자, 특수문자를 1개 이상 포함해야 함.
	private boolean isValidPassword(String password) {
	    return password != null && password.length() >= 8 && password.length() <= 16 &&
	           password.matches(".*[a-z].*") && password.matches(".*\\d.*") &&
	           password.matches(".*[!@#$%^&*()\\-_=+\\[\\]{}|;:'\",.<>?/].*");
	}
}
