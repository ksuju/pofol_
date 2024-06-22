package com.portfolio.www.contact;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.portfolio.www.service.ContactService;


@Controller
public class ContactController {
	
	@Autowired
	ContactService contactService;
	
	@RequestMapping("resume.do")
	public ModelAndView sendResume(@RequestParam String name,
			@RequestParam String email) {
		System.out.println("========= ContactController > resume =========");
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		mv.setViewName("auth/contact");

		boolean result = contactService.sendResume(name, email);
		
		if(result) {
			System.out.println("이력서 전송에 성공했습니다.");
			return mv;
		} else {
			System.out.println("이력서 전송에 실패했습니다.");
			return mv;
		}
	}
}
