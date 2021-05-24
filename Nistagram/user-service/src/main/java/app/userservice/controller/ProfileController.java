package app.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import app.userservice.dto.*;
import app.userservice.service.*;
import app.userservice.validator.ProfileValidator;

@RestController
@RequestMapping(value = "api/profile")
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
	
	@PutMapping
	public ResponseEntity<?> update(@RequestBody ProfileDTO profileDTO) {
		try {
			ProfileValidator.validate(profileDTO);
			profileService.update(profileDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
}
