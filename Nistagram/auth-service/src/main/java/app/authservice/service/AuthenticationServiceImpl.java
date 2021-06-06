package app.authservice.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import app.authservice.authentication.JwtAuthenticationRequest;
import app.authservice.model.User;
import app.authservice.security.TokenUtils;

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
	public String login(JwtAuthenticationRequest jwtAuthenticationRequest) {
		Authentication authentication = authenticationManager.authenticate
				(new UsernamePasswordAuthenticationToken(jwtAuthenticationRequest.getUsername(),jwtAuthenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        Set<String> authorities = user.getAuthorities().stream().map(authority -> authority.getName()).collect(Collectors.toSet());
        String jwt = tokenUtils.generateToken(user.getUsername(), authorities);
        return jwt;
	}

}
