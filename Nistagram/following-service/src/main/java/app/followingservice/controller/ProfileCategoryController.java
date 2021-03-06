package app.followingservice.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.followingservice.dto.ProfileCategoryDTO;
import app.followingservice.model.ProfileCategory;
import app.followingservice.service.ProfileCategoryService;
import app.followingservice.validator.ProfileCategoryValidator;

@RestController
@RequestMapping(value = "api/following/profile-category")
public class ProfileCategoryController {
	private ProfileCategoryService profileCategoryService;
	
	@Autowired
	public ProfileCategoryController(ProfileCategoryService profileCategoryService) {
		this.profileCategoryService = profileCategoryService;
	}

	@GetMapping("/all")
	public ResponseEntity<?> findAllProfileCategories(){
		
		try {
			Collection<ProfileCategoryDTO> categories = profileCategoryService.getAllProfileCategories();
			return new ResponseEntity<Collection<ProfileCategoryDTO>>(categories, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{username}")
	public ResponseEntity<?> findProfileCategoriesByUsername(@PathVariable String username){
		
		try {
			Collection<ProfileCategoryDTO> categories = profileCategoryService.getProfileCategoriesByUsername(username);
			return new ResponseEntity<Collection<ProfileCategoryDTO>>(categories, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAuthority('createFriendship')")
	@PutMapping("/{username}/{category}")
	public ResponseEntity<?> addProfilesCategory(@PathVariable String username, @PathVariable String category){
		
		try {
			profileCategoryService.addProfilesCategory(username, category);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<String>("An error occurred while adding category.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(path = "/create-category", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<?> addProfileCategory(@RequestBody ProfileCategoryDTO profileCategoryDTO){
		
		try {
			ProfileCategoryValidator.validate(profileCategoryDTO);
			profileCategoryService.addNewProfileCategory(profileCategoryDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		catch(Exception exception) {
			return new ResponseEntity<String>("An error occurred while adding category.", HttpStatus.BAD_REQUEST);
		}
	}
	
}
