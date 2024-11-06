package com.portfolio.www.index;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.portfolio.www.service.IndexService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {
	
	private final IndexService indexService;
	
	// 인덱스페이지
	@RequestMapping("/index.do")
	public ModelAndView index(@RequestParam HashMap<String, String> params) throws Exception {
		ModelAndView mv = new ModelAndView();
		System.out.println("================ IndexContoller 진입 ================");
		mv.setViewName("index");
		
		String rssUrl = "https://ksuju.tistory.com/rss";
		
		List<Map<String, String>> rssItems = indexService.blogRssAndParsing(rssUrl);
		
		mv.addObject("rssItems", rssItems);
		
		return mv;
	}
}
