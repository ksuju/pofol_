package com.portfolio.www.service;

import org.springframework.stereotype.Service;

import com.portfolio.www.dao.mybatis.ResumeRepository;
import com.portfolio.www.dto.EmailDto;
import com.portfolio.www.dto.ResumeDto;
import com.portfolio.www.util.EmailUtil;

import lombok.RequiredArgsConstructor;

@Service("contactService")
@RequiredArgsConstructor
public class ContactService {
	
	private final ResumeRepository resumeRepository;
	
	private final EmailUtil emailUtil;
	
	public boolean sendResume(String name, String email) {
		
		try {
			// 인증 메일 발송
			EmailDto sendResume = new EmailDto();
			sendResume.setReceiver(email);
			sendResume.setSubject(name+"님 안녕하세요! 강성준 이력서 보내드립니다.");
			// host + contextRoot + URI
			String text = "매사 항상 성실하게, 모르는 것은 배우고자 하는 자세로 기꺼이 임하겠습니다!";
			sendResume.setText(text);
			
			// 이력서 가져오기
			ResumeDto resume = resumeRepository.resume();
			
            if (resume != null && resume.getFileData() != null) {
                sendResume.setResume(resume.getFileData()); // 이력서 파일 데이터 설정
                emailUtil.sendResume(sendResume, true);
                resumeRepository.resumeRec(name,email);
                return true;
            } else {
                throw new Exception("이력서 파일 데이터를 가져오지 못했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
	}

}
