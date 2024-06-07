package com.portfolio.www.util;

import javax.mail.internet.MimeMessage;

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
		 return sendMail(email,false);
	 }
	
	 public String sendMail(EmailDto email, boolean isHtml) {
	     try {
	         MimeMessage message = mailSender.createMimeMessage();
	         MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
	         messageHelper.setTo(email.getReceiver());
	         messageHelper.setFrom(emailProp.getUsername());
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