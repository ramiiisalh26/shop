package com.example.shop.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.LoggerFactory;


import org.slf4j.Logger;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailServices implements EmailSender{

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailServices.class);

    @Autowired
    private final JavaMailSender mailSender;

    @SuppressWarnings("null")
    @Override
    @Async
    public void send(String to, String email){
        try {
            // MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("hello@amigoscode.com");
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            LOGGER.error("Faild to send email", e);
            throw new IllegalStateException("faild to send email");
        }
    }
}
