package app.activityservice.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.activityservice.dto.ProfileDTO;
import app.activityservice.event.ProfileCanceledEvent;
import app.activityservice.model.Profile;
import app.activityservice.repository.ProfileRepository;

@Service
public class ProfileServiceImpl implements ProfileService {

	private ProfileRepository profileRepository;
	private final ApplicationEventPublisher publisher;
	
	@Autowired
	public ProfileServiceImpl(ProfileRepository profileRepository,ApplicationEventPublisher publisher) {
		this.profileRepository = profileRepository;
		this.publisher = publisher;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void create(ProfileDTO profileDTO) throws Exception {
	
		Profile profile = new Profile();
		
		profile.setUsername(profileDTO.username);
		profile.setAllowedTagging(profileDTO.allowedTagging);
		profile.setBlocked(false);
		
		profileRepository.save(profile);
		
		publishProfileCanceled(profileDTO.username, "An error occurred while creating profile in activity service.");
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
	public ProfileDTO findByUsername(String username) {
		Profile profile = profileRepository.findByUsername(username);
		return new ProfileDTO(profile.getUsername(), profile.isAllowedTagging(), profile.isBlocked());
	}

}
