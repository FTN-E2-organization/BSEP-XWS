package app.activityservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.activityservice.dto.AddProfileDTO;
import app.activityservice.model.Profile;
import app.activityservice.repository.ProfileRepository;

@Service
public class ProfileServiceImpl implements ProfileService {

	private ProfileRepository profileRepository;
	
	@Autowired
	public ProfileServiceImpl(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}

	@Override
	public void create(AddProfileDTO profileDTO) {
		Profile profile = new Profile();
		profile.setUsername(profileDTO.username);
		profile.setAllowedTagging(profileDTO.allowedTagging);
		profileRepository.save(profile);
	}
}
