package com.portfolio.www.forum.notice;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.portfolio.www.dto.BoardAttachDto;
import com.portfolio.www.dto.BoardCommentDto;
import com.portfolio.www.dto.BoardDto;
import com.portfolio.www.dto.BoardLikeDto;
import com.portfolio.www.dto.CommentLikeDto;
import com.portfolio.www.forum.notice.dto.PageHandler;
import com.portfolio.www.service.NoticeService;
import com.portfolio.www.service.ZipService;

@Controller
public class NoticeController {

	@Autowired
	ZipService zipService;

	@Autowired
	NoticeService noticeService;

	// 댓글 좋아요 or 싫어요
	@RequestMapping("/forum/notice/commentIsLike.do")
	@ResponseBody
	public int commentIsLike(@RequestParam("boardSeq") int boardSeq,
			@RequestParam("boardTypeSeq") int boardTypeSeq,
			@RequestParam("cmtIsLike") String cmtIsLike,
			@RequestParam("commentSeq") int commentSeq,
			HttpServletRequest request) {
		System.out.println("======================= NoticeController > commentIsLike =======================");
		
		HttpSession session = request.getSession();
		String logInUser = (String)session.getAttribute("logInUser");
		
		int memberSeq = -1;
		
		try {
			memberSeq = noticeService.getMemberSeq(logInUser);
		} catch (NullPointerException nep) {
			System.out.println("사용자 없음");
			// exception 던지거나 로그인 페이지로
		}
		CommentLikeDto commentLikeDto = new CommentLikeDto();
		
		commentLikeDto.setBoardTypeSeq(boardTypeSeq);
		commentLikeDto.setBoardSeq(boardSeq);
		commentLikeDto.setMemberSeq(memberSeq);
		commentLikeDto.setIsLike(cmtIsLike);
		commentLikeDto.setCommentSeq(commentSeq);

		// 좋아요 & 싫어요 정보 없는 댓글은 새로 db에 추가해주고
		// db에 데이터가 있으면(DuplicateKeyException) update 해준다
		try {
			return noticeService.commentUpDown(commentLikeDto);
		} catch (DuplicateKeyException de) {
			return noticeService.commentUpDownCvt(commentLikeDto);
		}
		
	}
	
	// 게시글 좋아요 or 싫어요
	@RequestMapping("/forum/notice/thumbUpDown.do")
	@ResponseBody
	public int thumbUpDown(@RequestParam("boardSeq") int boardSeq, @RequestParam("boardTypeSeq") int boardTypeSeq,
			@RequestParam("isLike") String isLike,
			HttpServletRequest request) {
		System.out.println("======================= NoticeController > thumbUpDown =======================");

		HttpSession session = request.getSession();
		String logInUser = (String)session.getAttribute("logInUser");
		
		int memberSeq = -1;
		
		try {
			memberSeq = noticeService.getMemberSeq(logInUser);
		} catch (NullPointerException nep) {
			System.out.println("사용자 없음");
			// exception 던지거나 로그인 페이지로
		}
		BoardLikeDto boardLikeDto = new BoardLikeDto();
		
		boardLikeDto.setBoardTypeSeq(boardTypeSeq);
		boardLikeDto.setBoardSeq(boardSeq);
		boardLikeDto.setMemberSeq(memberSeq);
		boardLikeDto.setIsLike(isLike);

		// 좋아요 & 싫어요 정보 없는 게시물은 새로 db에 추가해주고
		// db에 데이터가 있으면(DuplicateKeyException) update 해준다
		try {
			return noticeService.thumbUpDown(boardLikeDto);
		} catch (DuplicateKeyException de) {
			return noticeService.thumbUpDownCvt(boardLikeDto);
		}
		
	}

	// 댓글삭제
	@RequestMapping("/forum/notice/deleteComment.do")
	public String deleteComment(@RequestParam HashMap<String, Object> params) {
		System.out.println("======================= NoticeController > deleteComment =======================");

		noticeService.deleteComment(params);

		int boardSeq = Integer.parseInt((String) params.get("boardSeq"));
		int boardTypeSeq = Integer.parseInt((String) params.get("boardTypeSeq"));
		return "redirect:/forum/notice/readPage.do?boardTypeSeq=" + boardTypeSeq + "&boardSeq=" + boardSeq;
	}

	// 댓글수정
	@RequestMapping("/forum/notice/updateComment.do")
	public String updateComment(@RequestParam HashMap<String, Object> params) {
		System.out.println("======================= NoticeController > updateComment =======================");

		noticeService.updateComments(params);

		int boardSeq = Integer.parseInt((String) params.get("boardSeq"));
		int boardTypeSeq = Integer.parseInt((String) params.get("boardTypeSeq"));

		return "redirect:/forum/notice/readPage.do?boardTypeSeq=" + boardTypeSeq + "&boardSeq=" + boardSeq;
	}

	// 댓글작성
	@RequestMapping("/forum/notice/reply.do")
	@ResponseBody
	public int addComment(@RequestBody BoardCommentDto dto, HttpServletRequest request) {
		System.out.println("======================= NoticeController > reply.do =======================");

		if (noticeService.addComment(dto, request) == 1) {
			return 1;
		} else {
			return -1;
		}
	}

	// 수정페이지 파일삭제
	@RequestMapping("/forum/notice/deleteFile.do")
	public String deleteFile(@RequestParam HashMap<String, Object> params) {
		noticeService.deleteFile(params);

		Integer boardSeq = Integer.parseInt((String) params.get("boardSeq"));
		Integer boardTypeSeq = Integer.parseInt((String) params.get("boardTypeSeq"));
		String memberId = (String) params.get("memberId");

		return "redirect:/forum/notice/updatePage.do?boardSeq=" + boardSeq + "&boardTypeSeq=" + boardTypeSeq
				+ "&memberId=" + memberId;
	}

	// 모든 첨부파일 다운로드
	@RequestMapping("/forum/notice/downloadAll.do")
	public void downloadAll(Model model, @RequestParam Integer boardSeq, @RequestParam Integer boardTypeSeq,
			HttpServletResponse response) {

		System.out.println("============ NoticeController > downloadAll.do 진입 ============");

		HashMap<String, Object> boardInfo = noticeService.getReadBoard(boardSeq, boardTypeSeq);

		// 파일이름 저장할 List 생성
		List<String> fileNames = (List<String>) boardInfo.get("fileNames");
		// 파일이 현재 저장된 경로를 저장한 List 생성
		List<String> filePaths = (List<String>) boardInfo.get("filePaths");

		try {
			zipService.createZip(fileNames, filePaths);
			zipService.downloadZip(response);
			zipService.deleteZip();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 첨부파일 개별 다운로드
	@RequestMapping("/forum/notice/download.do")
	public String download(@RequestParam int attachSeq, Model model) {
		BoardAttachDto dto = noticeService.getDownloadFileInfo(attachSeq);
		File file = new File(dto.getSavePath());

		Map<String, Object> fileInfo = new HashMap<>();
		fileInfo.put("downloadFile", file);
		fileInfo.put("orgFileNm", dto.getOrgFileNm());
		model.addAttribute("fileInfo", fileInfo);

		return "fileDownloadView";
	}

	// 게시글 수정
	@RequestMapping("/forum/notice/updateBoard.do")
	public String updateBoard(@RequestParam HashMap<String, Object> params, ServletRequest request,
			@RequestParam(value = "attFile", required = false) MultipartFile[] attFiles) {

		System.out.println("======= NoticeController > updateBoard =======");

		int boardTypeSeq = Integer.parseInt((String) params.get("boardTypeSeq"));

		noticeService.updateBoard(params, request, attFiles);

		return "redirect:/forum/notice/listPage.do?bdTypeSeq=" + boardTypeSeq;
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

		HashMap<String, Object> getBoard = noticeService.getReadBoard(boardSeq, boardTypeSeq);

		List<BoardAttachDto> fileList = (List<BoardAttachDto>) getBoard.get("fileList");

		String title = (String) getBoard.get("title");
		String content = (String) getBoard.get("content");

		mv.addObject("title", title);
		mv.addObject("content", content);
		mv.addObject("boardTypeSeq", boardTypeSeq);
		mv.addObject("boardSeq", boardSeq);
		mv.addObject("memberId", memberId);
		mv.addObject("fileList", fileList);

		return mv;
	}

	// 게시글 작성
	@RequestMapping("/forum/notice/createBoard.do")
	public String createBoard(@RequestParam HashMap<String, Object> params, ServletRequest request,
			@RequestParam(value = "attFile", required = false) MultipartFile[] attFiles) {

		System.out.println("======= NoticeController > cteateBoard =======");

		String boardTypeSeq = (String) params.get("boardTypeSeq");

		String errorMsg = "로그인이 필요한 서비스입니다.";

		boolean createResult = noticeService.boardCreate(params, request, attFiles);

		if (createResult == false) {
			return "redirect:/forum/notice/listPage.do?bdTypeSeq=" + boardTypeSeq + "&errorMsg=" + errorMsg;
		} else {
			return "redirect:/forum/notice/listPage.do?bdTypeSeq=" + boardTypeSeq;
		}

	}

	// 게시글 삭제 > 댓글이 있을 경우 댓글도 다 같이 삭제해줘야 함 noticeService에서 처리할 것
	@RequestMapping("/forum/notice/deleteBoard.do")
	public String deleteBoard(@RequestParam String memberId, @RequestParam int boardTypeSeq,
			@RequestParam int boardSeq) {

		
		noticeService.boardDelete(memberId, boardTypeSeq, boardSeq);

		return "redirect:/forum/notice/listPage.do?bdTypeSeq=" + boardTypeSeq;
	}

	@RequestMapping("/forum/notice/listPage.do")
	public ModelAndView listPage(@RequestParam HashMap<String, String> params, HttpServletRequest request) {
		
		System.out.println("======= NoticeController > listPage =======");
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		mv.setViewName("forum/notice/list");

		// 방어 코딩 처음에 params 없어 에러 나니(기본값 설정)
		if (!params.containsKey("page")) {
			params.put("page", "1");
		}
		if (!params.containsKey("size")) {
			params.put("size", "10");
		}
		mv.addObject("bdTypeSeq", params.get("bdTypeSeq"));

		// 시작 인덱스 계산
		int page = Integer.parseInt(params.get("page"));
		int size = Integer.parseInt(params.get("size"));
		int start = (page - 1) * size;
		int totalCnt = noticeService.totalCnt(Integer.parseInt(params.get("bdTypeSeq")));

		// 페이징
		PageHandler pageHandler = new PageHandler(page, totalCnt, size);

		mv.addObject("startPage", pageHandler.getStartPage());
		mv.addObject("endPage", pageHandler.getEndPage());
		mv.addObject("currentPage", page);
		mv.addObject("hasPrev", pageHandler.isShowPrev());
		mv.addObject("hasNext", pageHandler.isShowNext());
		mv.addObject("totalCnt", totalCnt);
		// 페이징 끝

		// 게시글리스트 출력
		HashMap<String, Integer> boardList = new HashMap<String, Integer>();
		boardList.put("bdTypeSeq", Integer.parseInt(params.get("bdTypeSeq")));
		boardList.put("start", start);
		boardList.put("size", size);

		List<BoardDto> list = noticeService.getList(boardList);

		mv.addObject("list", list);
		// 게시글리스트 출력 끝

		HttpSession session = request.getSession();
		String loginMember = (String) session.getAttribute("logInUser");

		mv.addObject("loginMember", loginMember);

		return mv;
	}

	@RequestMapping("/forum/notice/writePage.do")
	public ModelAndView writePage(@RequestParam HashMap<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		mv.setViewName("forum/notice/write");

		int boardTypeSeq = Integer.parseInt(params.get("boardTypeSeq"));
		mv.addObject("boardTypeSeq", boardTypeSeq);

		return mv;
	}

	@RequestMapping("/forum/notice/readPage.do")
	public ModelAndView readPage(@RequestParam HashMap<String, String> params, ServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		mv.setViewName("forum/notice/read");

		System.out.println("========= NoticeController > readPage =========");

		int boardSeq = Integer.parseInt(params.get("boardSeq"));
		int boardTypeSeq = Integer.parseInt(params.get("boardTypeSeq"));

		// 클릭한 게시글 가져오기
		HashMap<String, Object> selectBoard = noticeService.getReadBoard(boardSeq, boardTypeSeq);

		List<BoardAttachDto> fileList = (List<BoardAttachDto>) selectBoard.get("fileList");

		// 현재 로그인한 사용자의 id 가져오기
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();

		String logInUser = (String) session.getAttribute("logInUser");
		
		int loginMemberSeq = noticeService.getMemberSeq(logInUser);
		
		String isLike = noticeService.selectIsLike(loginMemberSeq,boardSeq,boardTypeSeq);
		
		
		
		
		mv.addObject("title", selectBoard.get("title"));
		mv.addObject("content", selectBoard.get("content"));
		mv.addObject("memberId", selectBoard.get("memberId"));
		mv.addObject("regDtm", selectBoard.get("reg_dtm"));
		mv.addObject("comments", selectBoard.get("comments"));
		mv.addObject("fileList", fileList);
		mv.addObject("boardSeq", boardSeq);
		mv.addObject("boardTypeSeq", boardTypeSeq);
		mv.addObject("logInUser", logInUser);
		mv.addObject("isLike", isLike);
		mv.addObject("cmtIsLike", noticeService.commentIsLike(loginMemberSeq,boardSeq,boardTypeSeq));

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
