package app.authservice.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;import app.authservice.authentication.JwtAuthenticationRequest;
import app.authservice.dto.CodeDTO;
import app.authservice.dto.VerificationResponseDTO;
import app.authservice.exception.NotFoundException;
import app.authservice.model.Admin;
import app.authservice.model.Authority;
import app.authservice.model.CodeToken;
import app.authservice.model.Permission;
import app.authservice.model.Profile;
import app.authservice.model.RecoveryToken;
import app.authservice.model.User;
import app.authservice.repository.AdminRepository;
import app.authservice.repository.CodeTokenRepository;
import app.authservice.repository.ProfileRepository;
import app.authservice.security.TokenUtils;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	private TokenUtils tokenUtils;
	private AuthenticationManager authenticationManager;
	private ProfileRepository profileRepository;
	private AdminRepository adminRepository;
	private static Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
	private CodeTokenRepository codeTokenRepository;
	private EmailService emailService;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public AuthenticationServiceImpl(TokenUtils tokenUtils, AuthenticationManager authenticationManager,
			ProfileRepository profileRepository, AdminRepository adminRepository, CodeTokenRepository codeTokenRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
		this.tokenUtils = tokenUtils;
		this.authenticationManager = authenticationManager;
		this.profileRepository = profileRepository;
		this.adminRepository = adminRepository;
		this.codeTokenRepository = codeTokenRepository;
		this.emailService = emailService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public String login(JwtAuthenticationRequest jwtAuthenticationRequest){
		
		Authentication authentication = authenticationManager.authenticate
				(new UsernamePasswordAuthenticationToken(jwtAuthenticationRequest.getUsername(),
					jwtAuthenticationRequest.getPassword() + profileRepository.getSaltByUsername(jwtAuthenticationRequest.getUsername())));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        Set<String> authorities = user.getAuthorities().stream().map(authority -> authority.getName()).collect(Collectors.toSet());
        Set<String> permissions = new HashSet<>();
        
        for(Authority authority : user.getAuthorities()) {
        	Set<String> permissionsFromAuthority = authority.getPermissions().stream().map(permission -> permission.getName()).collect(Collectors.toSet());
        	permissions.addAll(permissionsFromAuthority);
        }
        
        String jwt = tokenUtils.generateToken(user.getUsername(), authorities, permissions);
        return jwt;
	}

	@Override
	public VerificationResponseDTO verify(String token) throws NotFoundException {
		String authToken = token.substring(7);
		String roles = "";
		String permissions = "";
        String username = tokenUtils.getUsernameFromToken(authToken);
        if (!profileRepository.existsByUsername(username)) {
        	if(!adminRepository.existsByUsername(username))
        		throw new NotFoundException("The user does not exist in the system.");
        	
        	Admin admin = adminRepository.findByUsername(username);
        	
            if (tokenUtils.validateToken(authToken, admin)) {
            	Set<Permission> allPermissions = new HashSet<>();
            	
                for (Authority authority : admin.getAuthorities()) {
                	allPermissions.addAll(authority.getPermissions());
                    roles += authority.getName() + "|";
                }
                roles = roles.substring(0, roles.length() - 1);
                
                for(Permission permission:allPermissions) {
                	permissions += permission.getName() + "|";
                }
                permissions = permissions.substring(0, permissions.length() - 1);
                
                return new VerificationResponseDTO(username, roles, permissions);
            } 
            return null;
        }
        
        Profile profile = profileRepository.findByUsername(username);
        try {
			log.info("User verifing profile: " + profile.getId());
		} catch (Exception e) {
		}
        if (tokenUtils.validateToken(authToken, profile)) {
        	Set<Permission> allPermissions = new HashSet<>();
        
            for (Authority authority : profile.getAuthorities()) {
            	allPermissions.addAll(authority.getPermissions());
                roles += authority.getName() + "|";     
            }
            roles = roles.substring(0, roles.length() - 1);
            
            for(Permission permission:allPermissions) {
            	permissions += permission.getName() + "|";
            }
            permissions = permissions.substring(0, permissions.length() - 1);
            
            return new VerificationResponseDTO(username, roles, permissions);
        } 
        return null;
    }

	@Override
	public void generateCode(String username) throws MailException, InterruptedException {
		Profile profile = profileRepository.findByUsername(username); 
        CodeToken token = codeTokenRepository.findByUser(profile);
		if(token == null) {
			token = new CodeToken();
			token.setUser(profile);
		}
		token.setExparationTime(LocalDateTime.now().plusMinutes(5));
		String tokenStr = UUID.randomUUID().toString().replace("-","").substring(0,6);
		token.setCode(passwordEncoder.encode(tokenStr));
		codeTokenRepository.save(token);
		token.setCode(tokenStr);
		emailService.sendCodeEmail(profile.getEmail(), token);
	}
	
	@Override
	public boolean checkCode(CodeDTO dto){
		Profile profile = profileRepository.findByUsername(dto.username); 
		CodeToken token = codeTokenRepository.findByUser(profile);
		
		if(token == null || !passwordEncoder.matches(dto.enteredCode, token.getCode()) || token.getExparationTime().isBefore(LocalDateTime.now())) {
			return false;
		}else {
			return true;
		}
	}
}
