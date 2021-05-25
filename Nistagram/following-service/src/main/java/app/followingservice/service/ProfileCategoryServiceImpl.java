package app.followingservice.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return profileCategoryRepository.getAllProfileCategory();
	}

}
