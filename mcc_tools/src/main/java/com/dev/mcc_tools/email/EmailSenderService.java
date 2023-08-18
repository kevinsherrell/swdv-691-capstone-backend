package com.dev.mcc_tools.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailSenderService extends JavaMailSenderImpl{

    public void sendEmail(String toEmail, String subject, String body) {
        setHost(System.getenv("EMAIL_HOST"));
        setPort(Integer.parseInt(System.getenv("EMAIL_PORT")));
        setUsername(System.getenv("EMAIL_USERNAME"));
        setPassword(System.getenv("EMAIL_PASSWORD"));

        Properties props = getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(System.getenv("EMAIL_TEST_SENDER"));
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        this.send(message);
    }

}
