
package com.siloamusa.utils.services;


import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.siloamusa.utils.dto.EmailDetails;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired 
    private JavaMailSender javaMailSender;

    @PostConstruct
    public void initDebug() {
        if (javaMailSender instanceof JavaMailSenderImpl mailSender) {
            // Force the JavaMail Session to use SLF4J
            mailSender.getSession().setDebug(true);
            mailSender.getSession().setDebugOut(new PrintStream(new OutputStream() {
                private final StringBuilder line = new StringBuilder();
                @Override
                public void write(int b) {
                    if (b == '\n') {
                        log.info("[JavaMail] {}", line.toString().trim());
                        line.setLength(0);
                    } else if (b != '\r') {
                        line.append((char) b);
                    }
                }
            }));
            log.info("JavaMail Debug routing initialized.");
        }
    }

    @Async("emailExecutor") // Reference the bean name from your config
    public void sendEmailAsync(EmailDetails details) {
        try {
            log.info("Processing email to: {}", Arrays.toString(details.getRecipient()));
            
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(details.getFrom());
            helper.setTo(details.getRecipient());
            helper.setSubject(details.getSubject());
            helper.setText(details.getMsgBody(), true); // 'true' enables HTML
            
            javaMailSender.send(message);
            log.info("Mail successfully sent via thread: {}", Thread.currentThread().getName());
            
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", details.getRecipient(), e.getMessage());
			log.error("StackTrace Error :", e);
        }
    }
}

