package com.portfolio.www.dao.mybatis;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.portfolio.www.dto.EmailAuthDto;
import com.portfolio.www.dto.MemberAuthDto;

public interface NoticeRepository {

	//회원가입
	public int joinMember(HashMap<String, String> params);
	
	//아이디중복검사
	public List<String> memberSelectAll();
	
	//로그인
	public String login(String memberID);
	
	
	//멤버 seq 가져오기
	public int getMemberSeq(String memberID);
	
	//멤버지우기
	public int deleteMember(int memberSeq);
	
	//addAuthInfo
	public int addAuthInfo(MemberAuthDto memberAuthDto);
	
	//authURI가져오기
	public List<HashMap<String, Object>> authURI();
	
	//updateAuth
	public int updateAuth(EmailAuthDto emailAuthDto);
	//updateMemAuth
	public int updateMemAuth(@Param("memberSeq") int memberSeq);
}
