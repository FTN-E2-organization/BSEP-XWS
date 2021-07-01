package app.campaignservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.campaignservice.dto.ProfileDTO;
import app.campaignservice.model.Profile;
import app.campaignservice.repository.ProfileRepository;

@Service
public class ProfileServiceImpl implements ProfileService{

	private ProfileRepository profileRepository;
	
	@Autowired
	public ProfileServiceImpl(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;		
	}
	
	@Override
	public void create(ProfileDTO profileDTO) {
		Profile profile = new Profile();
		
		profile.setUsername(profileDTO.username);
		profile.setPublic(profileDTO.isPublic);
		profile.setInfluencer(profileDTO.isInfluencer);
		profile.setBlocked(false);
		
		profileRepository.save(profile);
	}
	
	@Override
	public void updatePersonalData(String oldUsername, ProfileDTO profileDTO) {
		Profile profile = profileRepository.findByUsername(oldUsername);
				
		profile.setUsername(profileDTO.username);
		
		profileRepository.save(profile);
	}
	
	@Override
	public void updateProfilePrivacy(ProfileDTO profileDTO) {
		Profile profile = profileRepository.findByUsername(profileDTO.username);
		
		profile.setPublic(profileDTO.isPublic);
		
		profileRepository.save(profile);
	}
	
	@Override
	public void blockProfile(String username) {
		Profile profile = profileRepository.findByUsername(username);
		profile.setBlocked(true);
		profileRepository.save(profile);
	}
	
	@Override
	public boolean existsByUsername(String username) {
		return profileRepository.existsByUsername(username);
	}

	@Override
	public ProfileDTO findByUsername(String username) {
		Profile profile = profileRepository.findByUsername(username);
		return new ProfileDTO(profile.getUsername(), profile.isPublic(), profile.isInfluencer(), profile.isBlocked());
	}

	@Override
	public Long getIdByUsername(String ownerUsername) {
		return profileRepository.findByUsername(ownerUsername).getId();
	}

	@Override
	public void deleteByUsername(String username) {
		Profile profile = profileRepository.findByUsername(username);
		profileRepository.delete(profile);
		
	}
}
