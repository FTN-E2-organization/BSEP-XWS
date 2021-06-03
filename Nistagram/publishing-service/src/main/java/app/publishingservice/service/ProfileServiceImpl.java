package app.publishingservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import app.publishingservice.dto.ProfileDTO;
import app.publishingservice.model.Profile;
import app.publishingservice.repository.ProfileRepository;

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
		profile.setPublic(profileDTO.isPublic);
		profile.setAllowedTagging(profileDTO.allowedTagging);
		profile.setDeleted(profileDTO.isDeleted);
		
		profileRepository.save(profile);
	}
	
	@Override
	@Transactional
	public void update(String oldUsername, ProfileDTO profileDTO) {
		Profile profile = profileRepository.findByUsername(oldUsername);
				
		profile.setUsername(profileDTO.username);
		profile.setPublic(profileDTO.isPublic);
		profile.setDeleted(profileDTO.isDeleted);
		profile.setAllowedTagging(profileDTO.allowedTagging);
		
		profileRepository.save(profile);
	}
	
	@Override
	public boolean existsByUsername(String username) {
		return profileRepository.existsByUsername(username);
	}

}
