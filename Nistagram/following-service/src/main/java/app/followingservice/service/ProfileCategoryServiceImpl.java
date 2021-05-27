package app.followingservice.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.followingservice.dto.ProfileCategoryDTO;
import app.followingservice.model.ProfileCategory;
import app.followingservice.repository.ProfileCategoryRepository;

@Service
public class ProfileCategoryServiceImpl implements ProfileCategoryService{
	private ProfileCategoryRepository profileCategoryRepository;
	
	@Autowired
	public ProfileCategoryServiceImpl(ProfileCategoryRepository profileCategoryRepository) {
		this.profileCategoryRepository = profileCategoryRepository;
	}

	@Override
	public Collection<ProfileCategory> getAllProfileCategories() {
		return profileCategoryRepository.getAllProfileCategoryies();
	}

	@Override
	public Collection<ProfileCategory> getProfileCategoriesByUsername(String username) {
		return profileCategoryRepository.getProfileCategoriesByUsername(username);
	}

	@Override
	public void addProfilesCategory(String username, String categoryName) {
		profileCategoryRepository.addProfilesCategory(username, categoryName);
	}

	@Override
	public void addNewProfileCategory(ProfileCategoryDTO profileCategoryDTO) {
		ProfileCategory profileCategory = new ProfileCategory();
		profileCategory.setName(profileCategoryDTO.name);
		
		profileCategoryRepository.save(profileCategory);
	}

}
