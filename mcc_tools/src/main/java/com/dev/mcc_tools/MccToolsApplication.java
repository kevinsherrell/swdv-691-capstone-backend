package com.dev.mcc_tools;

import com.dev.mcc_tools.email.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.TimeZone;
@SpringBootApplication
public class MccToolsApplication {

	@Autowired
	private EmailSenderService senderService;
	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(MccToolsApplication.class, args
		);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void sendMail(){
		senderService.sendEmail(System.getenv("EMAIL_TEST_RECIPIENT"),"test email from application", "this is a test email from capstone project!");
	}
}
