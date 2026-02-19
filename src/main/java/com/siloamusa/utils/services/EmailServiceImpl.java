
package com.siloamusa.utils.services;


import java.util.Arrays;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
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
        }
    }
}

