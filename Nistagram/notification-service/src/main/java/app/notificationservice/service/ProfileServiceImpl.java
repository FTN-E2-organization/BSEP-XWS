package app.notificationservice.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.notificationservice.dto.ProfileDTO;
import app.notificationservice.repository.ProfileRepository;
import app.notificationservice.event.ProfileCanceledEvent;
import app.notificationservice.model.Profile;

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
	public void create(ProfileDTO profileDTO) throws Exception{
		Profile profile = new Profile();		
		profile.setUsername(profileDTO.getUsername());		
		profile.setBlocked(false);
		profileRepository.save(profile);	
		
		publishProfileCanceled(profileDTO.getUsername(), "An error occurred while creating profile in notification service.");
	}	
	
	private void publishProfileCanceled(String username, String reason) {
		ProfileCanceledEvent event = new ProfileCanceledEvent(UUID.randomUUID().toString(), username,reason);     
        publisher.publishEvent(event);
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
	
	
}
