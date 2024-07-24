package com.portfolio.www.util;

import javax.activation.DataSource;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.portfolio.www.dto.EmailDto;

public class EmailUtil {
	// property 설정으로 받아보기
	private JavaMailSender mailSender;

	@Autowired
	private EmailProp emailProp;

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public String sendMail(EmailDto email) {
		return sendMail(email, false);
	}

	public String sendMail(EmailDto email, boolean isHtml) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			messageHelper.setTo(email.getReceiver());
			messageHelper.setFrom(emailProp.getUsername(),"ksuju");
			messageHelper.setSubject(email.getSubject()); // 메일제목은 생략이 가능하다

			// messageHelper.setText(email.getText());
			messageHelper.setText(email.getText(), isHtml);

			mailSender.send(message);

		} catch (Exception e) {
			System.out.println(e);
			System.out.println("sendMail >>>> Exception 발생");
			return "Error";
		}
		return "Sucess";
	}

	public void sendResume(EmailDto email, boolean isHtml) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			messageHelper.setTo(email.getReceiver());
			// messageHelper.setFrom(emailProp.getUsername());
			messageHelper.setFrom(emailProp.getUsername(), "강성준");
			messageHelper.setSubject(email.getSubject()); // 메일제목은 생략이 가능하다
			messageHelper.setText(email.getText(), isHtml);

	        // 첨부 파일 추가
	        if (email.getResume() != null) {
	            // InputStream을 DataSource로 변환
	            DataSource dataSource = new ByteArrayDataSource(email.getResume(), "application/pdf");
	            messageHelper.addAttachment("강성준 이력서.pdf", dataSource);
	        }

			mailSender.send(message);

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}