package app.authservice.controller;

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
import app.authservice.dto.VerificationResponseDTO;
import app.authservice.exception.NotFoundException;
import app.authservice.service.AuthenticationService;

@RestController
@RequestMapping(value = "api/auth")
public class AuthenticationController {

	private AuthenticationService authenticationService;

	@Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
	
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody JwtAuthenticationRequest authenticationRequest) {
		try {	
			return new ResponseEntity<>(authenticationService.login(authenticationRequest), HttpStatus.OK);
		}catch (BadCredentialsException e) {
			return new ResponseEntity<>("Bad credentials!", HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
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
}
