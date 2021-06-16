package app.notificationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.notificationservice.service.ProfileService;
import app.notificationservice.dto.ProfileDTO;

@RestController
@RequestMapping(value = "api/notification/profile")
public class ProfileController {

	private ProfileService profileService;	
	
	@Autowired
	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}
	
	@PostMapping
	public ResponseEntity<?> createRegularUser(@RequestBody ProfileDTO profileDTO) {
		try {
			profileService.create(profileDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while creating user.", HttpStatus.BAD_REQUEST);
		}
	}	
	
}
