package com.portfolio.www.auth;

import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.portfolio.www.service.LoginService;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Controller
public class LoginController {
	
	@Autowired
	LoginService loginService;
	
	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest request) {
		
		System.out.println("==================logout진입==================");
	    //세션 무효화
	    request.getSession().invalidate();
	    
	    return "redirect:/index.do";
	}
	
	@RequestMapping("/auth/login.do")
	public String login(@RequestParam HashMap<String, String> params,
			HttpServletRequest request) {

		//아이디로 비밀번호 찾기
		String dbPasswd = loginService.login(params.get("memberID"));
		
		String passwd = params.get("passwd");
		
        BCrypt.Result result = BCrypt.verifyer().verify(passwd.toCharArray(), dbPasswd);
        
        System.out.println("result=====비밀번호확인========>"+result);
		
        if (result.validFormat && result.verified) {
            // 비밀번호가 일치하는 경우
            // 로그인 성공 처리
        	HttpSession session = request.getSession();
        	session.setAttribute("logInUser", params.get("memberID"));
        	return "redirect:/index.do";
        } else {
            // 비밀번호가 일치하지 않는 경우
            // 로그인 실패 처리
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
}
