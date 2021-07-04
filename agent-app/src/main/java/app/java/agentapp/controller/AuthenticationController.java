package app.java.agentapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import app.java.agentapp.authentication.JwtAuthenticationRequest;
import app.java.agentapp.model.User;
import app.java.agentapp.repository.AgentRepository;
import app.java.agentapp.repository.CustomerRepository;
import app.java.agentapp.service.AuthenticationService;
import app.java.agentapp.validator.UserValidator;


@RestController
@RequestMapping(value = "api/auth")
public class AuthenticationController {

	private AuthenticationService authenticationService;
	private AgentRepository agentRepository;
	private CustomerRepository customerRepository;

	@Autowired
    public AuthenticationController(AuthenticationService authenticationService, AgentRepository agentRepository,CustomerRepository customerRepository) {
        this.authenticationService = authenticationService;
        this.agentRepository = agentRepository;
        this.customerRepository = customerRepository;
    }
	
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody JwtAuthenticationRequest authenticationRequest) {
		try {
			UserValidator.loginUserValidation(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		} catch (Exception ve) {
			return new ResponseEntity<>(ve.getMessage(), HttpStatus.BAD_REQUEST);
		}
		User user = agentRepository.findByUsername(authenticationRequest.getUsername());
		if(user == null)
			user = customerRepository.findByUsername(authenticationRequest.getUsername());
		
		try {	
			return new ResponseEntity<>(authenticationService.login(authenticationRequest), HttpStatus.OK);
		}catch (BadCredentialsException e) {
			return new ResponseEntity<>("Bad credentials!", HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while sending request for log in.", HttpStatus.BAD_REQUEST);
		}
    }
	
}
