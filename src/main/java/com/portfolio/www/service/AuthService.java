package com.portfolio.www.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.www.dao.mybatis.NoticeRepository;

@Service
public class AuthService {
	
	@Autowired
	private NoticeRepository noticeRepository;
	
	
	
	// auth Y > N 변환
	public boolean cvtAuthYN(String email, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String memberID = (String) session.getAttribute("logInUser");
		boolean result = noticeRepository.updateMAuth(memberID);
		if(result) {
			// Y > N member 테이블
			noticeRepository.updateAuthNumToNull(email);
			
			Integer memberSeq = noticeRepository.getMemberSeq(memberID);
			// Y > N member_auth 테이블			
			noticeRepository.updateAuthaa(memberSeq);
			return true;
		}
		return false;
	}
	
	// 인증번호인증 (비밀번호찾기,아이디인증)
	public boolean authNum(String authNum, String email) {
		
		// 데이터베이스에서 이메일에 해당하는 인증번호를 조회
		String dbAuthNum = noticeRepository.authNumSelect(email) + "";
		
		// 데이터베이스 인증번호와 입력된 인증번호가 일치하고, 비어있지 않으면 true 반환
		if(dbAuthNum.equals(authNum) && !dbAuthNum.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

}