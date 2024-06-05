package com.portfolio.www.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.www.dao.mybatis.NoticeRepository;
import com.portfolio.www.dto.EmailAuthDto;
import com.portfolio.www.dto.EmailDto;
import com.portfolio.www.dto.MemberAuthDto;
import com.portfolio.www.message.MessageEnum;
import com.portfolio.www.util.EmailUtil;

import at.favre.lib.crypto.bcrypt.BCrypt;


@Service("joinService")
public class JoinService {
	
	@Autowired
	private NoticeRepository noticeRepository;
	
	@Autowired
	private EmailUtil emailutil;
	
	// auth_yn 변경하기 트랜잭션으로 member_auth 테이블과 member 테이블에 있는 auth_yn 컬럼을 동시에 바꿈
    @Transactional
    public boolean updateAuthAndMemAuth(EmailAuthDto emailAuthDto) {
    	
        // auth_yn 변경하기 (member_auth 테이블)
        int updateAuth = noticeRepository.updateAuth(emailAuthDto);
        // auth_yn 변경하기 (member 테이블)
        int updateMemAuth = noticeRepository.updateMemAuth(emailAuthDto.getMemberSeq());
        
        if(updateMemAuth == 1 && updateAuth == 1) {
        	return true;
        }
        return false;
    }
	
	// 이메일 인증하기
	public EmailAuthDto authURI(String authURI) {
		List<HashMap<String, Object>> dbAuthURI = noticeRepository.authURI();
		
		for(HashMap<String, Object> auth : dbAuthURI) {
		    String authUri = (String) auth.get("auth_uri");
		    if(authURI.equals(authUri)) {
		    	Integer memberSeq = (Integer)auth.get("member_seq");
		    	System.out.println("======service > authURI > email인증성공========");
		    	
		    	return new EmailAuthDto(memberSeq, authUri);
		    }
		    
		}
		System.out.println("======service > authURI > email인증실패========");
		return null;
	}
	
	//멤버지우기
	public int deleteMember(int memberSeq) {
		return noticeRepository.deleteMember(memberSeq);
	}
	//멤버시퀀스가져오기
	public int getMemberSeq(String memberID) {
		return noticeRepository.getMemberSeq(memberID);
	}
	
	public int idCheck(String idCheck) {
		System.out.println("========================service > idCheck========================");
		List<String> memberList = noticeRepository.memberSelectAll();
		if(memberList.contains(idCheck)) {
			System.out.println("========================service > idCheck > 아이디중복========================");
			return Integer.parseInt(MessageEnum.DUPL_ID.getCode());
		} else {
			System.out.println("========================service > idCheck > 아이디중복없음========================");
			return Integer.parseInt(MessageEnum.NO_DUPL_ID.getCode());
		}
	}
	
	public int joinMember(HashMap<String, String> params){
		System.out.println("========================service > joinMember========================");
		//BCrypt를 사용한 비밀번호 암호화
		String passwd = params.get("passwd");
		String encPasswd = BCrypt.withDefaults().hashToString(12, passwd.toCharArray());
		//System.out.println("encPasswd >>>>>>>>> " + encPasswd);
		//BCrypt.Result result = BCrypt.verifyer().verify(passwd.toCharArray(), encPasswd);
		//System.out.println("result.verified >>>>>>> " + result.verified);
		
		params.put("passwd", encPasswd);
		//BCrypt를 사용한 비밀번호 암호화 끝
		
		int cnt = noticeRepository.joinMember(params);
		int memberSeq = noticeRepository.getMemberSeq(params.get("memberID"));
		
		if(cnt == 1) {
			//인증 메일구조 만들기
			MemberAuthDto authDto = new MemberAuthDto();
			authDto.setMemberSeq(memberSeq);
			//UUID
			authDto.setAuthUri(UUID.randomUUID().toString().replaceAll("-",""));
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, 30); // 30분만 유효
			authDto.setExpireDtm(cal.getTimeInMillis());
			
			noticeRepository.addAuthInfo(authDto);
			
			//인증 메일 발송
			EmailDto email = new EmailDto();
			email.setReceiver(params.get("email"));
			email.setSubject("인증하세요.");
			// host + contextRoot + URI
			String html = "<a href='http://localhost:8080/pf/emailAuth.do?uri="+ authDto.getAuthUri() + "'>인증하기</a>";
			email.setText(html);
			
			emailutil.sendMail(email,true);
		}
		
		return cnt;
	}
	
}
