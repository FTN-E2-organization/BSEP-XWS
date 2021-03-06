package app.authservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import app.authservice.authentication.JwtAuthenticationRequest;
import app.authservice.dto.CodeDTO;
import app.authservice.dto.VerificationResponseDTO;
import app.authservice.exception.NotFoundException;
import app.authservice.model.User;
import app.authservice.repository.AdminRepository;
import app.authservice.repository.ProfileRepository;
import app.authservice.service.AuthenticationService;
import app.authservice.validator.ProfileValidator;

@RestController
@RequestMapping(value = "api/auth")
public class AuthenticationController {

	private AuthenticationService authenticationService;
	private ProfileRepository profileRepository;
	private AdminRepository adminRepository;
	private static Logger log = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
    public AuthenticationController(AuthenticationService authenticationService, ProfileRepository profileRepository,AdminRepository adminRepository) {
        this.authenticationService = authenticationService;
        this.profileRepository = profileRepository;
        this.adminRepository = adminRepository;
    }
	
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody JwtAuthenticationRequest authenticationRequest) {
		try {
			ProfileValidator.loginUserValidation(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		} catch (Exception ve) {
			return new ResponseEntity<>(ve.getMessage(), HttpStatus.BAD_REQUEST);
		}
		User profile = profileRepository.findByUsername(authenticationRequest.getUsername());
		if(profile == null)
			profile = adminRepository.findByUsername(authenticationRequest.getUsername());
		
		if(profile.isBlocked())
			return new ResponseEntity<>("Profile is blocked due to inappropriate content.", HttpStatus.BAD_REQUEST);
		
		try {	
			try {
				log.info("User login successful: " + profile.getId());
			} catch (Exception e) {
			}
			authenticationService.generateCode(profile.getUsername());
			return new ResponseEntity<>(authenticationService.login(authenticationRequest), HttpStatus.OK);
		}catch (BadCredentialsException e) {
			try {
				log.error("User login bad credentials: " + profile.getId());
			} catch (Exception exception) {
			}
			return new ResponseEntity<>("Bad credentials!", HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			try {
				log.error("User login error while sending request: " + profile.getId());
			} catch (Exception exception) {
			}
			return new ResponseEntity<>("An error occurred while sending request for log in.", HttpStatus.BAD_REQUEST);
		}
    }
	
	@PostMapping("/verify")
    public VerificationResponseDTO verify(@RequestBody String token) {
        try {
			return authenticationService.verify(token);
		} catch (NotFoundException e) {
			return null;
		}
    }
	
	@PostMapping("/code-check")
    public ResponseEntity<?> checkCode(@RequestBody CodeDTO dto) {
        try {
        	if(authenticationService.checkCode(dto)) {
                return new ResponseEntity<>(authenticationService.checkCode(dto), HttpStatus.OK);
        	}else {
        		return new ResponseEntity<>("Wrong or expired code!", HttpStatus.BAD_REQUEST);
        	}
		} catch (Exception e) {
			return new ResponseEntity<>("Wrong or expired code!", HttpStatus.BAD_REQUEST);
		}
    }
}
