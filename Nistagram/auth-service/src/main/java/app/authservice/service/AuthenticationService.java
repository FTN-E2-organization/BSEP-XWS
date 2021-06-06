package app.authservice.service;

import app.authservice.authentication.JwtAuthenticationRequest;

public interface AuthenticationService {

	String login(JwtAuthenticationRequest jwtAuthenticationRequest);
    
}
