package app.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import app.authservice.dto.*;
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
	
	@PostMapping
	public ResponseEntity<?> createRegularUser(@RequestBody ProfileDTO profileDTO) {
		try {
			ProfileValidator.validate(profileDTO);
			profileService.createRegularUser(profileDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PutMapping("/personal")
	public ResponseEntity<?> updatePersonalData(@RequestBody ProfileDTO profileDTO) {
		try {
			ProfileValidator.validate(profileDTO);
			
			/*Ovdje ce se iz tokena dobaviti sadasnji username*/
			String oldUsername = "pero123";
			profileService.updatePersonalData(oldUsername, profileDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
}
