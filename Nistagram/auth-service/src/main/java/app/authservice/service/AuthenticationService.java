package app.authservice.service;

import org.springframework.mail.MailException;

import app.authservice.authentication.JwtAuthenticationRequest;
import app.authservice.dto.CodeDTO;
import app.authservice.dto.VerificationResponseDTO;
import app.authservice.exception.NotFoundException;

public interface AuthenticationService {

	String login(JwtAuthenticationRequest jwtAuthenticationRequest);
	VerificationResponseDTO verify(String token)  throws NotFoundException;
	void generateCode(String username) throws MailException, InterruptedException;
	boolean checkCode(CodeDTO dto);
    
}
