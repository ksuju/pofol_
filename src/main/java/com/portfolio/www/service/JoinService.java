package com.portfolio.www.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.www.dao.mybatis.NoticeRepository;
import com.portfolio.www.message.MessageEnum;

import at.favre.lib.crypto.bcrypt.BCrypt;


@Service
public class JoinService {
	
	@Autowired
	private NoticeRepository noticeRepository;
	
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
		System.out.println("encPasswd >>>>>>>>> " + encPasswd);
		BCrypt.Result result = BCrypt.verifyer().verify(passwd.toCharArray(), encPasswd);
		System.out.println("result.verified >>>>>>> " + result.verified);
		
		params.put("passwd", encPasswd);
		//BCrypt를 사용한 비밀번호 암호화 끝
		
		return noticeRepository.joinMember(params);
	}

}
