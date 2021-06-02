package app.storyservice.controller;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.storyservice.dto.ProfileDTO;
import app.storyservice.model.Profile;
import app.storyservice.service.ProfileService;

@RestController
@RequestMapping(value = "api/story/profile")
public class ProfileController {

	private ProfileService profileService;
	
	@Autowired
	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}

	
	@GetMapping("/all")
	public ResponseEntity<?> findAllProfiles(){
		
		try {
			// Collection<Profile> profiles dobijem od following sve profile koje pratim
			Collection<Profile> p = new HashSet<Profile>();
			Collection<Profile> profiles = profileService.getAllProfiles();
			return new ResponseEntity<Collection<Profile>>(profiles, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	} 
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody ProfileDTO profileDTO){
		try {
			profileService.create(profileDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}

