package app.notificationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public void create(ProfileDTO profileDTO) {
		Profile profile = new Profile();		
		profile.setUsername(profileDTO.getUsername());		
		profileRepository.save(profile);		
	}	
	
	
	
}
