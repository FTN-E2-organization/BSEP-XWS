package app.storyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.storyservice.dto.ProfileDTO;
import app.storyservice.model.Profile;
import app.storyservice.repository.ProfileRepository;


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
		profile.setPublic(profileDTO.isPublic());
		profile.setDeleted(profileDTO.isDeleted());
		
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
	public void updateProfilePrivacy(ProfileDTO profileDTO) {
		Profile profile = profileRepository.getProfileByUsername(profileDTO.getUsername());
		
		profile.setPublic(profileDTO.isPublic());
		
		profileRepository.save(profile);
	}

	@Override
	public ProfileDTO findByUsername(String username) {
		Profile profile = profileRepository.getProfileByUsername(username);
		return new ProfileDTO(profile.getUsername(), profile.isPublic(), profile.isDeleted());
	}

}
