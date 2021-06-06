package app.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import app.authservice.dto.*;
import app.authservice.exception.ValidationException;
import app.authservice.model.CustomPrincipal;
import app.authservice.service.*;
import app.authservice.validator.ProfileValidator;

@RestController
@RequestMapping(value = "api/auth/profile")
public class ProfileController {

	private ProfileService profileService;
	
	@Autowired
	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}
	
	@PreAuthorize("hasAuthority('createProfile')")
	@PostMapping
	public ResponseEntity<?> createRegularUser(@RequestBody ProfileDTO profileDTO) {
		try {
			ProfileValidator.createProfileValidation(profileDTO);
			profileService.createRegularUser(profileDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (ValidationException ve) {
			return new ResponseEntity<String>(ve.getMessage(), HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PreAuthorize("hasAuthority('improveProfileAsAgent')")
	@PutMapping("/to-agent/{username}")
	public ResponseEntity<?> addAgentRoleToRegularUser(@PathVariable String username) {
		try {
			profileService.addAgentRoleToRegularUser(username);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PreAuthorize("hasAuthority('updateProfile')")
	@PutMapping("/personal")
	public ResponseEntity<?> updatePersonalData(@RequestBody ProfileDTO profileDTO) {
		try {
			ProfileValidator.updatePersonalDataValidation(profileDTO);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
			String oldUsername = principal.getUsername();
			
			profileService.updatePersonalData(oldUsername, profileDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (ValidationException ve) {
			return new ResponseEntity<String>(ve.getMessage(), HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<?> findProfileByUsername(@PathVariable String username){
		try {
			ProfileDTO profileDTO = profileService.getProfileByUsername(username);
			return new ResponseEntity<ProfileDTO>(profileDTO, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAuthority('findAllowTaggingProfile')")
	@GetMapping("/allowedTagging")
	public ResponseEntity<?> findAllowedTaggingProfiles(){
		try {
			return new ResponseEntity<>(profileService.findAllowTaggingProfileUsernames(), HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
