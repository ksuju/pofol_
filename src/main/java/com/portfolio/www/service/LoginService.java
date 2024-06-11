package com.portfolio.www.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.www.dao.mybatis.NoticeRepository;
import com.portfolio.www.dto.EmailDto;
import com.portfolio.www.message.MessageEnum;
import com.portfolio.www.util.EmailProp;
import com.portfolio.www.util.EmailUtil;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
public class LoginService {

	@Autowired
	private NoticeRepository noticeRepository;

	@Autowired
	private EmailUtil emailutil;

	@Autowired
	private EmailProp emailprop;

	// 비밀번호 변경
	public int changePasswd(HashMap<String, String> params) {
		// BCrypt를 사용한 비밀번호 암호화
		String passwd = params.get("passwd");
		String encPasswd = BCrypt.withDefaults().hashToString(12, passwd.toCharArray());
		// System.out.println("encPasswd >>>>>>>>> " + encPasswd);
		// System.out.println("result.verified >>>>>>> " + result.verified);

		long expireDtm = noticeRepository.getExpireDtm(params.get("email"));
		long now = System.currentTimeMillis();

		System.out.println("expireDtm============================>" + expireDtm);
		System.out.println("now============================>" + now);

		if (expireDtm > now) {
			params.put("passwd", encPasswd);
			// BCrypt를 사용한 비밀번호 암호화 끝
			// 인증번호 null로 바꾸기
			noticeRepository.updateAuthNumToNull(params.get("email"));

			return noticeRepository.changePasswd(params);
		} else {
			// 인증번호 null로 바꾸기
			noticeRepository.updateAuthNumToNull(params.get("email"));
			return Integer.parseInt(MessageEnum.EXPIRE_AUTH_DTM.getCode());
		}
	}

	// 비밀번호 변경을 위한 메일 인증 완료
	public int emailAuthPw(HashMap<String, String> params) {
		int dbAuthNum = noticeRepository.authNumSelect(params.get("email"));
		String authNumString = params.get("authNum");
		int authNum = 0;

		// controller로 부터 authNum을 제대로 받아왔을때
		if (authNumString != null) {
			authNum = Integer.parseInt(authNumString);

			// db에 저장된 auth_num과 인증메일로부터 받은 authNum이 일치하면
			if (dbAuthNum == authNum) {
				// 1(성공) 을 반환함
				return 1;
			}
		}
		// 실패
		return 0;
	}

	// 비밀번호 변경 인증번호 생성 및 메일 발송
	public int updateAuthNum(HashMap<String, String> params) {
		int cnt = noticeRepository.updateAuthNum(params);
		int emailExist = noticeRepository.emailCount(params.get("email"));
		
		if (emailExist == 0) {
			return Integer.parseInt(MessageEnum.VALLID_EMAIL.getCode());
		}
		
		if (cnt == 1) {
			// 인증 메일 발송
			EmailDto email = new EmailDto();
			email.setReceiver(params.get("email"));
			email.setSubject("비밀번호 변경 인증 메일입니다.");
			// host + contextRoot + URI
			String html = emailprop.getPwChangeUri() + params.get("authNum") + "&email=" + params.get("email")
					+ "'>비밀번호 변경하기</a>";

			email.setText(html);

			emailutil.sendMail(email, true);
			return cnt;
		}
		return cnt;
	}

	public int loginCheak(String memberID, String passwd, HttpServletRequest request) {
		String dbPasswd = noticeRepository.loginCheak(memberID);
		String getAuthYN = noticeRepository.getAuthYN(memberID);
		System.out.println("================ LoginService > loginCheak 진입 ================");
		
		if (dbPasswd != null && ("Y").equals(getAuthYN)) {	
			BCrypt.Result result = BCrypt.verifyer().verify(passwd.toCharArray(), dbPasswd);

			if(result.validFormat && result.verified) {
				// 비밀번호가 일치하는 경우
				// 로그인 성공 처리
				HttpSession session = request.getSession();
				session.setAttribute("logInUser", memberID);
				return Integer.parseInt(MessageEnum.SUCCESS.getCode());
			}
		} else if(!("Y").equals(getAuthYN)) {
			System.out.println("================== 인증되지 않은 아이디 ==================");
			return Integer.parseInt(MessageEnum.NOT_EMAIL_AUTH.getCode());
		}
		return Integer.parseInt(MessageEnum.FAILED.getCode());
	}
}
