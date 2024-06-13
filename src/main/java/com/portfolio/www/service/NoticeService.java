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
	
	public List<BoardDto> getList(HashMap<String, Integer> params){
		
		return noticeRepository.getList(params);
	}
	
	public int totalCnt(int bdTypeSeq) {
		return noticeRepository.totalCnt(bdTypeSeq);
	}
}
