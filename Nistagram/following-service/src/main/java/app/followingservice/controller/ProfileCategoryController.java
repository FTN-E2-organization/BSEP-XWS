package app.followingservice.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	public ResponseEntity<?> allProfileCategories(){
		
		try {
			Collection<ProfileCategory> categories = profileCategoryService.getAllProfileCategories();
			return new ResponseEntity<Collection<ProfileCategory>>(categories, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
