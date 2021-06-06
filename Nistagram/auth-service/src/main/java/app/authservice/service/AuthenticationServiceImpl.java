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
import app.authservice.dto.VerificationResponseDTO;
import app.authservice.exception.NotFoundException;
import app.authservice.model.Admin;
import app.authservice.model.Authority;
import app.authservice.model.Profile;
import app.authservice.model.User;
import app.authservice.repository.AdminRepository;
import app.authservice.repository.ProfileRepository;
import app.authservice.security.TokenUtils;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	private TokenUtils tokenUtils;
	private AuthenticationManager authenticationManager;
	private ProfileRepository profileRepository;
	private AdminRepository adminRepository;
	
	@Autowired
	public AuthenticationServiceImpl(TokenUtils tokenUtils, AuthenticationManager authenticationManager,
			ProfileRepository profileRepository, AdminRepository adminRepository) {
		this.tokenUtils = tokenUtils;
		this.authenticationManager = authenticationManager;
		this.profileRepository = profileRepository;
		this.adminRepository = adminRepository;
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

	@Override
	public VerificationResponseDTO verify(String token) throws NotFoundException {
		String authToken = token.substring(7);
		String roles = "";
        String username = tokenUtils.getUsernameFromToken(authToken);
        
        if (!profileRepository.existsByUsername(username)) {
        	if(!adminRepository.existsByUsername(username))
        		throw new NotFoundException("The user does not exist in the system.");
        	
        	Admin admin = adminRepository.findByUsername(username);
        	
            if (tokenUtils.validateToken(authToken, admin)) {
                for (Authority authority : admin.getAuthorities()) {
                    roles += authority.getName() + "|";
                }
                roles = roles.substring(0, roles.length() - 1);
                return new VerificationResponseDTO(username, roles);
            } 
            return null;
        }
        
        Profile profile = profileRepository.findByUsername(username);
        if (tokenUtils.validateToken(authToken, profile)) {
            for (Authority authority : profile.getAuthorities()) {
                roles += authority.getName() + "|";
            }
            roles = roles.substring(0, roles.length() - 1);
            return new VerificationResponseDTO(username, roles);
        } 
        return null;
    }
	
}
