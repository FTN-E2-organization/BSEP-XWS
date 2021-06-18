package app.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.authservice.dto.AgentRegistrationRequestDTO;
import app.authservice.exception.ValidationException;
import app.authservice.mapper.AgentRegistrationRequestMapper;
import app.authservice.model.CustomPrincipal;
import app.authservice.service.AgentRegistrationService;
import app.authservice.service.ProfileService;
import app.authservice.validator.ProfileValidator;

@RestController
@RequestMapping(value = "api/auth/agent-registration")
public class AgentRegistrationController {

	private AgentRegistrationService agentRegistrationService;
	private ProfileService profileService;
	
	@Autowired
	public AgentRegistrationController(AgentRegistrationService agentRegistrationService, ProfileService profileService) {
		this.agentRegistrationService = agentRegistrationService;
		this.profileService = profileService;
	}
	
	@PreAuthorize("hasAuthority('createAgentRegistrationRequest')")
	@PostMapping
	public ResponseEntity<?> create(@RequestBody AgentRegistrationRequestDTO requestDTO){
		try {
			ProfileValidator.checkEmailFormat(requestDTO.email);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
	        requestDTO.username = principal.getUsername();
	        
			agentRegistrationService.create(requestDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (ValidationException ve) {
			return new ResponseEntity<String>(ve.getMessage(), HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while creating agent registration request.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('getAllAgentRequestsByUsername')")
	@GetMapping()
	public ResponseEntity<?> getAllByUsername(){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
	        String username = principal.getUsername();
	        
			return new ResponseEntity<>(AgentRegistrationRequestMapper.toRequestDTOs(agentRegistrationService.findAllByUsername(username)), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting dissaproved requests.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('manageAgentRegistrationRequest')")
	@GetMapping("/disapproved")
	public ResponseEntity<?> getAllDisapproved(){
		try {
			return new ResponseEntity<>(AgentRegistrationRequestMapper.toRequestDTOs(agentRegistrationService.getAllDisapproved()), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting dissaproved requests.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('manageAgentRegistrationRequest')")
	@PutMapping("/approve/{id}")
	public ResponseEntity<?> approveRequest(@PathVariable Long id){
		try {
			agentRegistrationService.approveRequest(id);
			profileService.addAgentRoleToRegularUser(AgentRegistrationRequestMapper.toDto(agentRegistrationService.findById(id)));
			
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while approving requests.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('manageAgentRegistrationRequest')")
	@PutMapping("/disapprove/{id}")
	public ResponseEntity<?> disapproveRequest(@PathVariable Long id){
		try {
			agentRegistrationService.disapproveRequest(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while disapproving requests.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAnyAuthority('manageAgentRegistrationRequest', 'createAgentRegistrationRequest')")
	@PutMapping("/delete/{id}")
	public ResponseEntity<?> deleteRequest(@PathVariable Long id){
		try {
			agentRegistrationService.deleteRequest(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while deleting requests.", HttpStatus.BAD_REQUEST);
		}
	}
}
