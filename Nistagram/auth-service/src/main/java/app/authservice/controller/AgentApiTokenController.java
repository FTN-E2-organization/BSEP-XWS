package app.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.authservice.service.AgentApiTokenService;

@RestController
@RequestMapping(value = "api/auth/agent-api-token")
public class AgentApiTokenController {
	
	private AgentApiTokenService agentApiTokenService;
	
	@Autowired
	public AgentApiTokenController(AgentApiTokenService agentApiTokenService) {
		this.agentApiTokenService = agentApiTokenService;
	}
	
	@GetMapping("/check/{token}")
	public ResponseEntity<?> checkToken(@PathVariable String token){
		try {
			return new ResponseEntity<Boolean>(agentApiTokenService.checkToken(token), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while checkong agent API token.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('getApiToken')")
	@GetMapping("/token/{username}")
	public ResponseEntity<?> getTokenByUsername(@PathVariable String username){
		try {
			return new ResponseEntity<>(agentApiTokenService.findTokenByUsername(username), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting agent API token.", HttpStatus.BAD_REQUEST);
		}
	}

}
