
package com.siloamusa.utils.services;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.siloamusa.utils.dto.EmailDetails;

@Service
// Implementing EmailService interface
public class EmailServiceImpl implements Runnable {

	@Autowired private 
	JavaMailSender javaMailSender;

	private EmailDetails emldetail;

	public void setInfo(EmailDetails details) { emldetail= details;}

	public void startProcess() {
		Thread t = new Thread(this);
		t.start();
	}


	@Override
	public void run() {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			message.setFrom(new InternetAddress(emldetail.getFrom()));
			for (String recipient : emldetail.getRecipient()) {
				if(recipient == null || recipient.isEmpty()) {
					continue; // Skip null or empty recipients
				}
				message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipient));
			}
			message.setSubject(emldetail.getSubject());
			message.setContent(emldetail.getMsgBody(),  "text/html; charset=utf-8");
			javaMailSender.send(message);
			System.out.println("Mail Sent Successfully to ..." + emldetail.getRecipient());
		}catch (Exception e) {
			e.printStackTrace();		
		}
	}

}
