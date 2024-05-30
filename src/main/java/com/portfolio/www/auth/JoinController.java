package com.portfolio.www.auth;

import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.portfolio.www.service.JoinService;

@Controller
public class JoinController {
	
	@Autowired
	JoinService joinService;
	
	
	//joinPage 진입
	@RequestMapping("/auth/joinPage.do")
	public ModelAndView joinPage(@RequestParam HashMap<String, String> params,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		mv.setViewName("auth/join");
		
		System.out.println("==============================JoinController > joinPage.do진입==============================");
		return mv;
	}
	
	
	// 아이디 중복체크 기능
	@ResponseBody
	@RequestMapping("/auth/idCheck.do")
	public int idCheck(@RequestParam String idCheck,
			HttpServletRequest request) {
		System.out.println("==============================JoinController > idCheck.do진입==============================");
		int idCheckCode = joinService.idCheck(idCheck);
		
		if(idCheckCode == 1) {
			System.out.println("controller > idCheck > 아이디중복");
			System.out.println("==============================JoinController > idCheck.do종료==============================");
			return idCheckCode;
		} else {
			System.out.println("controller > idCheck > 아이디중복없음");
			System.out.println("==============================JoinController > idCheck.do종료==============================");
			return idCheckCode;
		}
	}
	
	// 회원가입 기능  조건별로 알람 넣던가 해야함 enum 활용?
	@ResponseBody
	@RequestMapping("/auth/join.do")
	public int join(@RequestParam HashMap<String, String> params,
			HttpServletRequest request) {
		
		System.out.println("==============================JoinController > join.do진입==============================");
		
		if(params.isEmpty()) {
			return 0;
		} else {
			System.out.println("join params 확인 ========================>"+params);
			
			// 비밀번호와 확인용 비밀번호가 일치하는지 확인하는 조건
			if (!params.get("passwd").isEmpty() && !params.get("con_pass").isEmpty() && params.get("passwd").equals(params.get("con_pass"))) {
			    // 아이디가 사용 가능한지 확인하는 조건
			    if (!params.get("successMessage").isEmpty()) {
			        // 비밀번호와 확인용 비밀번호가 모두 비어 있지 않고, 일치하며, 아이디가 사용 가능한 경우
			    	int joinCheck = joinService.joinMember(params);
			    	
					//회원가입 성공시 1 반환
					if(joinCheck == 1) {
						System.out.println("=====================회원가입 성공 확인=====================");
						System.out.println("==============================JoinController > join.do종료==============================");
						return 1;
						//회원가입 실패시 0 반환
					} else {
						System.out.println("=====================회원가입 실패 확인=====================");
						System.out.println("==============================JoinController > join.do종료==============================");
						return 0;
					}
			    }
			}
			return 0;
		}
	}
}
