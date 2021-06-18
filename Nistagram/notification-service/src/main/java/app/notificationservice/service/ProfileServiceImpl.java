package app.notificationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.notificationservice.dto.ProfileDTO;
import app.notificationservice.repository.ProfileRepository;
import app.notificationservice.model.Profile;

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
		profile.setUsername(profileDTO.getUsername());		
		profile.setBlocked(false);
		profileRepository.save(profile);		
	}	
	
	@Override
	@Transactional
	public void updatePersonalData(String oldUsername, ProfileDTO profileDTO) {
		Profile profile = profileRepository.getProfileByUsername(oldUsername);
		profile.setUsername(profileDTO.getUsername());
		profileRepository.save(profile);
	}
	
	@Override
	@Transactional
	public void blockProfile(String username) {
		Profile profile = profileRepository.getProfileByUsername(username);
		profile.setBlocked(true);
		profileRepository.save(profile);
	}
	
	
}
