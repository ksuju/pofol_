package com.portfolio.www.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.www.dao.mybatis.NoticeRepository;
import com.portfolio.www.dto.BoardDto;


@Service
public class NoticeService {
	
	@Autowired
	private NoticeRepository noticeRepository;
	
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
