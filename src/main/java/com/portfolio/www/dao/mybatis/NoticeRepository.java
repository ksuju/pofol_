package com.portfolio.www.dao.mybatis;

import java.util.HashMap;
import java.util.List;

public interface NoticeRepository {

	//회원가입
	public int joinMember(HashMap<String, String> params);
	
	//아이디중복검사
	public List<String> memberSelectAll();
	
	//로그인
	public String login(String memberID);
}
