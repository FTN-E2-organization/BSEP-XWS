package app.followingservice.service;

import java.util.ArrayList;
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
	public Collection<ProfileCategoryDTO> getAllProfileCategories() {
		Collection<ProfileCategory> categories = profileCategoryRepository.getAllProfileCategoryies();
		Collection<ProfileCategoryDTO> categoryDTOs = new ArrayList<>();
		for(ProfileCategory p: categories) {
			categoryDTOs.add(new ProfileCategoryDTO(p.getId(), p.getName()));
		}
		return categoryDTOs;
	}

	@Override
	public Collection<ProfileCategoryDTO> getProfileCategoriesByUsername(String username) {
		Collection<ProfileCategory> categories = profileCategoryRepository.getProfileCategoriesByUsername(username);
		Collection<ProfileCategoryDTO> categoryDTOs = new ArrayList<>();
		for(ProfileCategory p: categories) {
			categoryDTOs.add(new ProfileCategoryDTO(p.getId(), p.getName()));
		}
		return categoryDTOs;
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
