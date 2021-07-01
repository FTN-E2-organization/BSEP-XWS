package app.publishingservice.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.publishingservice.event.ProfileCanceledEvent;
import app.publishingservice.dto.ProfileDTO;
import app.publishingservice.model.Profile;
import app.publishingservice.repository.ProfileRepository;

@Service
public class ProfileServiceImpl implements ProfileService {
	
	private ProfileRepository profileRepository;
	private final ApplicationEventPublisher publisher;
	
	@Autowired
	public ProfileServiceImpl(ProfileRepository profileRepository, ApplicationEventPublisher publisher) {
		this.profileRepository = profileRepository;
		this.publisher = publisher;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void create(ProfileDTO profileDTO) throws Exception {
		Profile profile = new Profile();
		
		profile.setUsername(profileDTO.username);
		profile.setPublic(profileDTO.isPublic);
		profile.setAllowedTagging(profileDTO.allowedTagging);
		profile.setBlocked(false);
		
		profileRepository.save(profile);
				
		publishProfileCanceled(profileDTO.username, "An error occurred while creating profile in publishing service.");
	}
	
	private void publishProfileCanceled(String username, String reason) {
		ProfileCanceledEvent event = new ProfileCanceledEvent(UUID.randomUUID().toString(), username,reason);     
        publisher.publishEvent(event);
    }
	
	@Override
	@Transactional
	public void updatePersonalData(String oldUsername, ProfileDTO profileDTO) {
		Profile profile = profileRepository.findByUsername(oldUsername);
				
		profile.setUsername(profileDTO.username);
		
		profileRepository.save(profile);
	}
	
	@Override
	@Transactional
	public void updateProfilePrivacy(ProfileDTO profileDTO) {
		Profile profile = profileRepository.findByUsername(profileDTO.username);
		
		profile.setPublic(profileDTO.isPublic);
		profile.setAllowedTagging(profileDTO.allowedTagging);
		
		profileRepository.save(profile);
	}
	
	@Override
	@Transactional
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
		return new ProfileDTO(profile.getUsername(), profile.isPublic(), profile.isAllowedTagging(), profile.isBlocked());
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
