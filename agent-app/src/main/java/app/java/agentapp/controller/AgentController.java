package app.java.agentapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.java.agentapp.dto.AgentDTO;
import app.java.agentapp.exception.BadRequest;
import app.java.agentapp.exception.ValidationException;
import app.java.agentapp.service.AgentService;
import app.java.agentapp.validator.AgentValidator;

@RestController
@RequestMapping(value = "api/agent")
public class AgentController {
	
	private AgentService agentService;
	
	@Autowired
	public AgentController(AgentService agentService) {
		this.agentService = agentService;
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> createAgent(@RequestBody AgentDTO agentDTO) {
		try {
			AgentValidator.createAgentValidation(agentDTO);
			agentService.createAgent(agentDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (ValidationException ve) {
			return new ResponseEntity<String>(ve.getMessage(), HttpStatus.BAD_REQUEST);
		}catch (BadRequest be) {
			return new ResponseEntity<String>(be.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while registering agent.", HttpStatus.BAD_REQUEST);
		}
	}
}
