package com.portfolio.www.forum.notice;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.portfolio.www.dto.BoardDto;
import com.portfolio.www.forum.notice.dto.PageHandler;
import com.portfolio.www.service.NoticeService;

@Controller
public class NoticeController {
	
	@Autowired
	NoticeService noticeService;
	
	@RequestMapping("/forum/notice/listPage.do")
	public ModelAndView listPage(@RequestParam HashMap<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		mv.setViewName("forum/notice/list");
		
		//방어 코딩 처음에 params 없어 에러 나니(기본값 설정)
		if(!params.containsKey("page")) {
			params.put("page", "1");
		}
		if(!params.containsKey("size")) {
			params.put("size", "10");
		}
		mv.addObject("bdTypeSeq",params.get("bdTypeSeq"));
		
		// 시작 인덱스 계산
	    int page = Integer.parseInt(params.get("page"));
	    int size = Integer.parseInt(params.get("size"));
	    int start = (page - 1) * size;
	    int totalCnt = noticeService.totalCnt(Integer.parseInt(params.get("bdTypeSeq")));
	    
	    // 페이징
		PageHandler pageHandler = new PageHandler(page,totalCnt,size);
		
		mv.addObject("startPage",pageHandler.getStartPage());
		mv.addObject("endPage",pageHandler.getEndPage());
		mv.addObject("currentPage",page);
		mv.addObject("hasPrev",pageHandler.isShowPrev());
		mv.addObject("hasNext",pageHandler.isShowNext());
		// 페이징 끝
		
		// 게시글리스트 출력
		HashMap<String, Integer> boardList = new HashMap<String, Integer>();
		boardList.put("bdTypeSeq", Integer.parseInt(params.get("bdTypeSeq")));
		boardList.put("start", start);
		boardList.put("size", size);
		
		List<BoardDto> list = noticeService.getList(boardList);
		
		mv.addObject("list",list);
		// 게시글리스트 출력 끝
		return mv;
	}
	
	@RequestMapping("/forum/notice/writePage.do")
	public ModelAndView writePage(@RequestParam HashMap<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		mv.setViewName("forum/notice/write");
		
		return mv;
	}
	
	@RequestMapping("/forum/notice/readPage.do")
	public ModelAndView readPage(@RequestParam HashMap<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		mv.setViewName("forum/notice/read");
		
		return mv;
	}
	
	
	@RequestMapping("/contact.do")
	public ModelAndView contactView() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		mv.setViewName("auth/contact");
		
		return mv;
	}

}
