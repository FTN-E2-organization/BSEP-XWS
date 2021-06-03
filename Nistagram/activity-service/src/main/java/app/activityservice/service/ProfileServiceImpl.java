package app.activityservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.activityservice.dto.ProfileDTO;
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
	@Transactional
	public void create(ProfileDTO profileDTO) {
		Profile profile = new Profile();
		
		profile.setUsername(profileDTO.username);
		profile.setAllowedTagging(profileDTO.allowedTagging);
		profile.setDeleted(profileDTO.isDeleted);
		
		profileRepository.save(profile);
	}

	@Override
	@Transactional
	public void updatePersonalData(String oldUsername, ProfileDTO profileDTO) {
		oldUsername = "sladjica";
		Profile profile = profileRepository.findByUsername(oldUsername);
		
		profile.setUsername(profileDTO.username);
		
		profileRepository.save(profile);
	}
}
