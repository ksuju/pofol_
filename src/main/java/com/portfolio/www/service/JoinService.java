package com.portfolio.www.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.www.dao.mybatis.NoticeRepository;


@Service
public class JoinService {
	
	@Autowired
	private NoticeRepository noticeRepository;
	
	
	public int joinMember(HashMap<String, String> params){
		return noticeRepository.joinMember(params);
	}

}
