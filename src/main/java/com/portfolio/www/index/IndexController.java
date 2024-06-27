package com.portfolio.www.index;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.portfolio.www.service.IndexService;

@Controller
public class IndexController {
	
	@Autowired
	IndexService indexService;
	
	// 인덱스페이지 개별 메뉴 추천
	@ResponseBody
	@RequestMapping(value = "/recomMenu.do", produces = "text/plain; charset=UTF-8") // 한글인식 안되는 문제 해결
	public String recomMenu(@RequestParam String category) {
		
		String menu = ""; 
		
		if(category.equals("아무거나")) {
			menu = indexService.allMenu();
		} else {
			menu = indexService.getMenu(category);
		}
		
		return menu;
	}
	
	
	// 인덱스페이지
	@RequestMapping("/index.do")
	public ModelAndView index(@RequestParam HashMap<String, String> params) {
		ModelAndView mv = new ModelAndView();
		
		System.out.println("================ IndexContoller 진입 ================");

		mv.setViewName("index");
		
		// 모든 메뉴 카테고리 가져오기
		List<String> category = indexService.menuCategory();
		mv.addObject("category",category);
		
		// 모든 메뉴 가져오기
		String menu = indexService.allMenu();
		mv.addObject("menu",menu);
		return mv;
	}
}
