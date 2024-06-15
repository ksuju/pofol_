package com.portfolio.www.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.www.dao.mybatis.NoticeRepository;
import com.portfolio.www.dto.BoardDto;


@Service
public class NoticeService {
	
	@Autowired
	private NoticeRepository noticeRepository;
	
	// 게시글 수정
	public int updateBoard (HashMap<String, Object> params, ServletRequest request) {
		
		System.out.println("======= NoticeService > updateBoard =======");
		
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Date nowDate = new Date();
	    String now = sdf.format(nowDate);
	    
	    params.put("now", now);
	    
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		
		String logInUser = (String) session.getAttribute("logInUser");
		
		int memberSeq = noticeRepository.getMemberSeq((String)params.get("memberId"));
		
		params.put("memberSeq", memberSeq);
		
		if(logInUser.isEmpty()) {
			return 0;
		} else {
			return noticeRepository.updateBoard(params);
		}
		
	}
	
	public HashMap<String, Object> getReadBoard(int boardSeq, int boardTypeSeq) {
		System.out.println("============getReadBoard > boardInfo=================");
		HashMap<String, Object> boardInfo = new HashMap<String, Object>();
		
		boardInfo = selectBoard(boardSeq, boardTypeSeq);

		// reg_member_seq=45, reg_dtm=2024-06-14, title=asd, content=asdasd}
		
	    Integer regMemberSeq = (Integer) boardInfo.get("reg_member_seq");
		
		String memberId = noticeRepository.selectMemberId(regMemberSeq).get("member_id");
		
		boardInfo.put("memberId", memberId);
		
		return boardInfo;
	}
	
	// 게시글 하나만 가져오기
    public HashMap<String, Object> selectBoard(int boardSeq, int boardTypeSeq) {
    	return noticeRepository.selectBoard(boardSeq, boardTypeSeq);
    }
    
	// 게시글 작성
	public int boardCreate (int boardTypeSeq, String title, String content, ServletRequest request) {
		System.out.println("=========================== service > boardCreate ===========================");
		
		
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Date nowDate = new Date();
	    String now = sdf.format(nowDate);
	    
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		
		String logInUser = (String) session.getAttribute("logInUser");
		
		if(logInUser.isEmpty()) {
			return 0;
		} else {
			int memberSeq = noticeRepository.getMemberSeq(logInUser);
			return noticeRepository.boardCreate(boardTypeSeq, title, content, memberSeq, now);
		}
	    
		
	}
	
	// 게시글 삭제하기
	public int boardDelete(String memberId, int boardTypeSeq, int boardSeq) {
		System.out.println("=========================== service > boardDelete ===========================");
		return noticeRepository.boardDelete(memberId, boardTypeSeq, boardSeq);
		
	}
	
	// 게시판 내 게시글 불러오기
	public List<BoardDto> getList(HashMap<String, Integer> params){
		
		return noticeRepository.getList(params);
	}
	
	// 게시판 내 총 게시글 수
	public int totalCnt(int bdTypeSeq) {
		return noticeRepository.totalCnt(bdTypeSeq);
	}
}
