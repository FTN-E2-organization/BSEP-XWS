package app.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import app.authservice.model.CodeToken;
import app.authservice.model.ConfirmationToken;
import app.authservice.model.RecoveryToken;

@Service
public class EmailServiceImpl implements EmailService {

	private JavaMailSender mailSender;
	private Environment environment;
	
	@Autowired 
	public EmailServiceImpl(JavaMailSender mailSender, Environment environment) {
		this.mailSender = mailSender;
		this.environment = environment;
	}
	
	@Override
	@Async
	public void sendActivationEmail(String email, ConfirmationToken confirmationToken) throws MailException, InterruptedException {
		SimpleMailMessage mailMessage = new SimpleMailMessage();		
		String port = "8111";
		mailMessage.setTo(email);
		mailMessage.setFrom(environment.getProperty("spring.mail.username"));
		mailMessage.setSubject("Complete Registration!");
		mailMessage.setText("To confirm your account, please click here: "
	            +"https://localhost:" + port + "/api/auth/profile/confirm-account?token=" + confirmationToken.getConfirmationToken());
		mailSender.send(mailMessage);			
	}

	@Override
	@Async
	public void sendInformationEmail(String email, String text) throws MailException, InterruptedException {
		SimpleMailMessage mailMessage = new SimpleMailMessage();		
		mailMessage.setTo(email);
		mailMessage.setFrom(environment.getProperty("spring.mail.username"));
		mailMessage.setSubject("Registration");
		mailMessage.setText(text);
		mailSender.send(mailMessage);			
	}	
	
	@Override
	@Async
	public void sendRecoveryEmail(String email, RecoveryToken recoveryToken) throws MailException, InterruptedException {
		SimpleMailMessage mailMessage = new SimpleMailMessage();		
		String port = "8111";
		mailMessage.setTo(email);
		mailMessage.setFrom(environment.getProperty("spring.mail.username"));
		mailMessage.setSubject("Password recovery");
		mailMessage.setText("This address is associated with the login," + email + ". To set a new password, please click the following link:"
	            +"https://localhost:"+ port  + "/html/change_password.html?token=" + recoveryToken.getRecoveryToken());
		mailSender.send(mailMessage);			
	}	
	
	@Override
	@Async
	public void sendCodeEmail(String email, CodeToken codetoken) throws MailException, InterruptedException {
		SimpleMailMessage mailMessage = new SimpleMailMessage();		
		String port = "8111";
		mailMessage.setTo(email);
		mailMessage.setFrom(environment.getProperty("spring.mail.username"));
		mailMessage.setSubject("Two factor authentication code");
		mailMessage.setText("This address is associated with the login," + email + ". To login successfully please enter the following code:"
							+ codetoken.getCode());
		mailSender.send(mailMessage);			
	}	
}
