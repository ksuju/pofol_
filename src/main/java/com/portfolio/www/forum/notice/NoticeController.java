package com.portfolio.www.forum.notice;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	
	// 게시글 수정
	@RequestMapping("/forum/notice/updateBoard.do")
	public String updateBoard(@RequestParam HashMap<String, Object> params,
			ServletRequest request) {	
		
		System.out.println("======= NoticeController > updateBoard =======");
		
		int boardTypeSeq = Integer.parseInt((String)params.get("boardTypeSeq"));
		
		noticeService.updateBoard(params, request);
		
		return "redirect:/forum/notice/listPage.do?bdTypeSeq="+ boardTypeSeq;
	}
	
	// 게시글 수정 jsp 이동
	@RequestMapping("/forum/notice/updatePage.do")
	public ModelAndView updatePage(@RequestParam HashMap<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		mv.setViewName("forum/notice/update");
		
		System.out.println("======= NoticeController > updatePage =======");
		
		int boardSeq = Integer.parseInt(params.get("boardSeq"));
		int boardTypeSeq = Integer.parseInt(params.get("boardTypeSeq"));
		String memberId = params.get("memberId");
		
		HashMap<String, Object> getBoard = new HashMap<String, Object>();
		
		getBoard = noticeService.selectBoard(boardSeq, boardTypeSeq);
		
		String title =(String)getBoard.get("title");
		String content = (String)getBoard.get("content");
		
		mv.addObject("title",title);
		mv.addObject("content",content);
		mv.addObject("boardTypeSeq",boardTypeSeq);
		mv.addObject("boardSeq",boardSeq);
		mv.addObject("memberId",memberId);
		
		return mv;
	}
	
	// 게시글 작성
	@RequestMapping("/forum/notice/createBoard.do")
	public String createBoard(@RequestParam HashMap<String, String> params,
			ServletRequest request) {
		
		System.out.println("======= NoticeController > cteateBoard =======");
		
		int boardTypeSeq = Integer.parseInt(params.get("boardTypeSeq"));
		String content = params.get("trumbowyg-demo");
		String title = params.get("title");
		
		String errorMsg = "로그인이 필요한 서비스입니다.";
		
		int createResult = noticeService.boardCreate(boardTypeSeq, title, content, request);
		
		if(createResult == 0) {
			return "redirect:/forum/notice/listPage.do?bdTypeSeq="+ boardTypeSeq + "&errorMsg=" + errorMsg;
		} else {
			return "redirect:/forum/notice/listPage.do?bdTypeSeq="+ boardTypeSeq;
		}
		

	}
	
	// 게시글 삭제 > 댓글이 있을 경우 댓글도 다 같이 삭제해줘야 함 noticeService에서 처리할 것
	@RequestMapping("/forum/notice/deleteBoard.do")
	public String deleteBoard(@RequestParam String memberId,
			@RequestParam int boardTypeSeq,
			@RequestParam int boardSeq) {
		
		noticeService.boardDelete(memberId, boardTypeSeq, boardSeq);
		
		return "redirect:/forum/notice/listPage.do?bdTypeSeq=" + boardTypeSeq;
	}
	
	@RequestMapping("/forum/notice/listPage.do")
	public ModelAndView listPage(@RequestParam HashMap<String, String> params,
			HttpServletRequest request) {
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
		
		HttpSession session = request.getSession();
		String loginMember = (String)session.getAttribute("logInUser");
		
		mv.addObject("loginMember",loginMember);
		
		return mv;
	}
	
	@RequestMapping("/forum/notice/writePage.do")
	public ModelAndView writePage(@RequestParam HashMap<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		mv.setViewName("forum/notice/write");
		
		int boardTypeSeq = Integer.parseInt(params.get("boardTypeSeq"));
		mv.addObject("boardTypeSeq",boardTypeSeq);
		
		return mv;
	}
	
	@RequestMapping("/forum/notice/readPage.do")
	public ModelAndView readPage(@RequestParam HashMap<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		mv.setViewName("forum/notice/read");
		
		System.out.println("========= NoticeController > readPage =========");

		int boardSeq = Integer.parseInt(params.get("boardSeq"));
		int boardTypeSeq = Integer.parseInt(params.get("boardTypeSeq"));
		
		HashMap<String, Object> selectBoard = new HashMap<String, Object>();
		// 클릭한 게시글 가져오기
		selectBoard = noticeService.getReadBoard(boardSeq, boardTypeSeq);
		//{reg_member_seq=45, reg_dtm=2024-06-14, title=asd, content=asdasd, memberId=asd}
		
		mv.addObject("title", selectBoard.get("title"));
		mv.addObject("content", selectBoard.get("content"));
		mv.addObject("memberId", selectBoard.get("memberId"));
		mv.addObject("regDtm", selectBoard.get("reg_dtm"));
		
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
