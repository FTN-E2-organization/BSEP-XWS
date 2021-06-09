package app.authservice.service;

import org.springframework.mail.MailException;

import app.authservice.model.ConfirmationToken;

public interface EmailService {

	void sendActivationEmail(String email, ConfirmationToken confirmationToken) throws MailException, InterruptedException;

	void sendInformationEmail(String email, String text) throws MailException, InterruptedException;
	
}
