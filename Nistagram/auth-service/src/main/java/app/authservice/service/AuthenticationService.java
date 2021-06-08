package app.authservice.service;

import app.authservice.authentication.JwtAuthenticationRequest;
import app.authservice.dto.VerificationResponseDTO;
import app.authservice.exception.NotFoundException;

public interface AuthenticationService {

	String login(JwtAuthenticationRequest jwtAuthenticationRequest);
	VerificationResponseDTO verify(String token)  throws NotFoundException;
    
}
