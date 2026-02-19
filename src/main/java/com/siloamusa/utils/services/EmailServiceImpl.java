
package com.siloamusa.utils.services;

import java.io.OutputStream;
import java.io.PrintStream;

import javax.annotation.PostConstruct;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import com.siloamusa.utils.dto.EmailDetails;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
// Implementing EmailService interface
public class EmailServiceImpl implements Runnable {

	@Autowired private 
	JavaMailSender javaMailSender;

	@PostConstruct
    public void init() {
        if (javaMailSender instanceof JavaMailSenderImpl mailSender) {
            // Create a custom PrintStream that routes to @Slf4j
            PrintStream slf4jPrintStream = new PrintStream(new OutputStream() {
                private StringBuilder line = new StringBuilder();

                @Override
                public void write(int b) {
                    if (b == '\n') {
                        log.debug("[JavaMail] {}", line.toString());
                        line.setLength(0);
                    } else if (b != '\r') {
                        line.append((char) b);
                    }
                }
            });

            // Apply to the underlying JavaMail Session
            mailSender.getSession().setDebugOut(slf4jPrintStream);
            mailSender.getSession().setDebug(true); // Ensure debug is active
        }
    }
	
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
				log.info("Mail Sent to :{}", recipient);
				message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipient));
			}
			message.setSubject(emldetail.getSubject());
			message.setContent(emldetail.getMsgBody(),  "text/html; charset=utf-8");
			javaMailSender.send(message);
			//System.out.println("Mail Sent Successfully to ..." + emldetail.getRecipient());
			log.info("Mail Sent Successfully to ... :{}", (Object[]) emldetail.getRecipient());
		}catch (Exception e) {
			e.printStackTrace();		
		}
	}

}
