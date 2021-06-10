package app.authservice.service;

import org.springframework.mail.MailException;

import app.authservice.model.ConfirmationToken;
import app.authservice.model.RecoveryToken;

public interface EmailService {

	void sendActivationEmail(String email, ConfirmationToken confirmationToken) throws MailException, InterruptedException;

	void sendInformationEmail(String email, String text) throws MailException, InterruptedException;
	
	void sendRecoveryEmail(String email, RecoveryToken token)
			throws MailException, InterruptedException;
}
