package app.followingservice.service;

import java.util.Collection;

import app.followingservice.dto.ProfileCategoryDTO;
import app.followingservice.model.ProfileCategory;

public interface ProfileCategoryService {
	
	Collection<ProfileCategory> getAllProfileCategories();
	Collection<ProfileCategory> getProfileCategoriesByUsername(String username);
	void addProfilesCategory(String username, String categoryName);
	void addNewProfileCategory(ProfileCategoryDTO profileCategoryDTO);
}
