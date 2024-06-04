package com.portfolio.www.util;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.portfolio.www.dto.EmailDto;

public class EmailUtil {
	  // property 설정으로 받아보기
	 private JavaMailSender mailSender;
	 private String fromEmail;
	 
	 public void setMailSender(JavaMailSender mailSender) {
		 this.mailSender = mailSender;
	 }
	 
	 public void setFromEmail(String fromEmail) {
		 this.fromEmail = fromEmail;
	 }
	 
	 public String sendMail(EmailDto email) {
		 return sendMail(email,false);
	 }
	
	 public String sendMail(EmailDto email, boolean isHtml) {
	     try {
	         MimeMessage message = mailSender.createMimeMessage();
	         MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
	         messageHelper.setTo(email.getReceiver());
	         messageHelper.setFrom(this.fromEmail);
	         messageHelper.setSubject(email.getSubject());	// 메일제목은 생략이 가능하다
	
	//         messageHelper.setText(email.getText());
	         messageHelper.setText(email.getText(),isHtml);
	         
	         mailSender.send(message);
	
	     } catch(Exception e){
	         System.out.println(e);
	         return "Error";
	     }
	     return "Sucess";
	 }
}