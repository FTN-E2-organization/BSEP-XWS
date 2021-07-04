package app.followingservice.service;

import java.util.Collection;

import app.followingservice.dto.ProfileCategoryDTO;

public interface ProfileCategoryService {
	
	Collection<ProfileCategoryDTO> getAllProfileCategories();
	Collection<ProfileCategoryDTO> getProfileCategoriesByUsername(String username);
	void addProfilesCategory(String username, String categoryName);
	void addNewProfileCategory(ProfileCategoryDTO profileCategoryDTO);
}
