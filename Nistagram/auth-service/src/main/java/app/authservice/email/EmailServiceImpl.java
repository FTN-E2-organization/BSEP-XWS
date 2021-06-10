package app.authservice.email;

import org.springframework.mail.MailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import app.authservice.model.RecoveryToken;

import org.springframework.core.env.Environment;

@Service
public class EmailServiceImpl implements EmailService {

	private JavaMailSender mailSender;
	private Environment environment;	
	
	@Autowired public EmailServiceImpl(JavaMailSender mailSender, Environment environment) {
		this.mailSender = mailSender;
		this.environment = environment;
	}
	
	
	@Override
	@Async
	public void sendRecoveryEmail(String email, RecoveryToken recoveryToken) throws MailException, InterruptedException {
		SimpleMailMessage mailMessage = new SimpleMailMessage();		
		String port = environment.getProperty("local.server.port");
		mailMessage.setTo(email);
		mailMessage.setFrom(environment.getProperty("spring.mail.username"));
		mailMessage.setSubject("Password recovery");
		mailMessage.setText("This address is associated with the login," + email + ". To set a new password, please click the following link:"
	            +"https://localhost:" + port + "/html/change_password.html?token=" + recoveryToken.getRecoveryToken());
		mailSender.send(mailMessage);			
	}	

}