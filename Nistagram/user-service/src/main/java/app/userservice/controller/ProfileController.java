package app.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import app.userservice.dto.AddProfileDTO;
import app.userservice.model.*;
import app.userservice.service.*;

@RestController
@RequestMapping(value = "api/profile")
public class ProfileController {

	private ProfileService profileService;
	
	@Autowired
	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}
	
	@PostMapping
	public ResponseEntity<?> addRegularUser(@RequestBody AddProfileDTO profileDTO) {
		try {
			return new ResponseEntity<Profile>(profileService.addRegularUser(profileDTO), HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
}
