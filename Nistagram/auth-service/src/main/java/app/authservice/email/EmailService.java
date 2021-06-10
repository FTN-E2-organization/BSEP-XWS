package app.authservice.email;

import org.springframework.mail.MailException;

import app.authservice.model.RecoveryToken;

public interface EmailService {

	void sendRecoveryEmail(String email, RecoveryToken token)
			throws MailException, InterruptedException;
	
}