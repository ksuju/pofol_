package com.portfolio.www.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.www.dao.mybatis.NoticeRepository;

@Service
public class LoginService {
	
	@Autowired
	private NoticeRepository noticeRepository;
	
	public String login(String memberID) {
		
		return noticeRepository.login(memberID);
	}
}
