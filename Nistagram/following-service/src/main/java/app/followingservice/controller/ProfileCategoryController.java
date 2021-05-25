package app.followingservice.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import app.followingservice.model.ProfileCategory;
import app.followingservice.service.ProfileCategoryService;

@RestController
@RequestMapping(value = "api/profile-category")
public class ProfileCategoryController {
	private ProfileCategoryService profileCategoryService;
	
	@Autowired
	public ProfileCategoryController(ProfileCategoryService profileCategoryService) {
		this.profileCategoryService = profileCategoryService;
	}

	@GetMapping("/all")
	public ResponseEntity<?> findAllProfileCategories(){
		
		try {
			Collection<ProfileCategory> categories = profileCategoryService.getAllProfileCategories();
			return new ResponseEntity<Collection<ProfileCategory>>(categories, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{username}")
	public ResponseEntity<?> findProfileCategoriesByUsername(@PathVariable String username){
		
		try {
			Collection<ProfileCategory> categories = profileCategoryService.getProfileCategoriesByUsername(username);
			return new ResponseEntity<Collection<ProfileCategory>>(categories, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/{username}/{category}")
	public ResponseEntity<?> findProfileCategoriesByUsername(@PathVariable String username, @PathVariable String category){
		
		try {
			profileCategoryService.addUsersProfileCategory(username, category);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
