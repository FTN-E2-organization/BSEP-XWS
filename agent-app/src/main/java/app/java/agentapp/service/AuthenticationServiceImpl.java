package app.java.agentapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import app.java.agentapp.authentication.JwtAuthenticationRequest;
import app.java.agentapp.model.User;
import app.java.agentapp.security.TokenUtils;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	private TokenUtils tokenUtils;
	private AuthenticationManager authenticationManager;
	
	@Autowired
	public AuthenticationServiceImpl(TokenUtils tokenUtils, AuthenticationManager authenticationManager) {
		this.tokenUtils = tokenUtils;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public String login(JwtAuthenticationRequest jwtAuthenticationRequest){
		
		Authentication authentication;
		
		try {
			authentication = authenticationManager.authenticate
					(new UsernamePasswordAuthenticationToken(jwtAuthenticationRequest.getUsername(), jwtAuthenticationRequest.getPassword()));
		}catch (Exception e) {
			authentication = authenticationManager.authenticate
					(new UsernamePasswordAuthenticationToken(jwtAuthenticationRequest.getUsername(), jwtAuthenticationRequest.getPassword()));
		}

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        
        String jwt = tokenUtils.generateToken(user.getUsername(), user.getAuthority().getName());
        return jwt;
	}

	
}
