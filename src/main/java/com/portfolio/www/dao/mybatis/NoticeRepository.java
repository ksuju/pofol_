package com.portfolio.www.dao.mybatis;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.portfolio.www.dto.EmailAuthDto;
import com.portfolio.www.dto.MemberAuthDto;

public interface NoticeRepository {
	
	// 인증만료시간 가져오기
	public long getExpireDtm(String email);
	
	// 비밀번호 변경
	public int changePasswd(HashMap<String, String> params);
	
	// authNumSelect db에 저장된 authNum 조회
	public Integer authNumSelect(String email);
	
	// auth_num update to null
	public int updateAuthNumToNull(String email);
	
	// auth_num update
	public int updateAuthNum(HashMap<String, String> params);

	//회원가입
	public int joinMember(HashMap<String, String> params);
	
	//아이디 중복검사
	public List<String> memberSelectAll();
	
	//email 중복검사
	public List<String> emailSelectAll();
	
	//로그인
	public String loginCheak(String memberID);
	
	
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
