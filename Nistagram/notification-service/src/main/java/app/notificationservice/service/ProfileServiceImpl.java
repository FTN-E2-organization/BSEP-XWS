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
		Profile profile = profileRepository.findProfileByUsername(oldUsername);
		profile.setUsername(profileDTO.getUsername());
		profileRepository.save(profile);
	}
	
	@Override
	@Transactional
	public void blockProfile(String username) {
		Profile profile = profileRepository.findProfileByUsername(username);
		profile.setBlocked(true);
		profileRepository.save(profile);
	}

	@Override
	public ProfileDTO findByUsername(String username) {
		Profile profile = profileRepository.findProfileByUsername(username);
		return new ProfileDTO(profile.getUsername(), profile.isBlocked());
	}

	@Override
	public void deleteProfileByUsername(String username) {
		Profile profile = profileRepository.findProfileByUsername(username);
		profileRepository.delete(profile);
	}
	
	
}
