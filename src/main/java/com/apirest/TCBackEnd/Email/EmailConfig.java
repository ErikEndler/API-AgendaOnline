package com.apirest.TCBackEnd.Email;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration

public class EmailConfig {
	
	@Bean
	public JavaMailSender mailSender() {
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		mailSender.setHost("smtp.live.com");
		mailSender.setPort(25);
		mailSender.setUsername("medicamentosolidario@hotmail.com");
		mailSender.setPassword("medicamento@2020");
		
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtps.starttls.enable", "true");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.put("mail.debug", "true");
		props.put("mail.smtp.connectiontimeout", 5000);			
		mailSender.setJavaMailProperties(props);			
		return mailSender;			
	}

}
