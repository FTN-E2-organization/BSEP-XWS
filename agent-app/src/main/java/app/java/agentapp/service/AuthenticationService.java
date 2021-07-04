package app.java.agentapp.service;

import app.java.agentapp.authentication.JwtAuthenticationRequest;

public interface AuthenticationService {

	String login(JwtAuthenticationRequest jwtAuthenticationRequest);    
}
