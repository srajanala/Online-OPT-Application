package com.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SendEmail {
	@Autowired
    private JavaMailSender mailSender;
	
	public JavaMailSender getMailSender() {
		return mailSender;
	}
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public boolean Send(String recipientAddress, String subject, String message){
		System.out.println("Sendmail"+mailSender);
		SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);
	  
        // sends the e-mail
        try{
        mailSender.send(email);
        }catch(Exception ex){
        	System.out.println("Mail Send Failed");
        	return false;
        }
        return true;
	}

}
