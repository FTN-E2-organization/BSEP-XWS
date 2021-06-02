package app.publishingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import app.publishingservice.service.ProfileService;

@RestController
@RequestMapping(value = "api/publishing/profile")
public class ProfileController {

	private ProfileService profileService;
	
	@Autowired
	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}
	
	//Ova metoda je suvisna ali za sada neka stoji zbog testiranja
	/*@PostMapping
	public ResponseEntity<?> create(@RequestBody ProfileDTO profileDTO){
		try {
			profileService.create(profileDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}*/
}
