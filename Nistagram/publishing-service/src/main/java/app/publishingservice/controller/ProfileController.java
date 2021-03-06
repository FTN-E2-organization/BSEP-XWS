package app.publishingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.publishingservice.dto.ProfileDTO;
import app.publishingservice.service.ProfileService;

@RestController
@RequestMapping(value = "api/publishing/profile")
public class ProfileController {

	private ProfileService profileService;
	
	@Autowired
	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<?> findProfileByUsername(@PathVariable String username){
		try {
			ProfileDTO profileDTO = profileService.findByUsername(username);
			return new ResponseEntity<ProfileDTO>(profileDTO, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
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
	
	@GetMapping("/post-owner/{id}")
	public ResponseEntity<?> getOwnerOfPost(@PathVariable Long id){
		try {
			ProfileDTO profileDTO = profileService.getOwnerOfPost(id);
			return new ResponseEntity<ProfileDTO>(profileDTO, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/story-owner/{id}")
	public ResponseEntity<?> getOwnerOfStory(@PathVariable Long id){
		try {
			ProfileDTO profileDTO = profileService.getOwnerOfStory(id);
			return new ResponseEntity<ProfileDTO>(profileDTO, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
