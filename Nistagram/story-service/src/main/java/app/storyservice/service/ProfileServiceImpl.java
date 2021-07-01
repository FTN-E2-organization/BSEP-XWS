package app.storyservice.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.storyservice.event.ProfileCanceledEvent;
import app.storyservice.dto.ProfileDTO;
import app.storyservice.model.Profile;
import app.storyservice.repository.ProfileRepository;


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
		profile.setPublic(profileDTO.isPublic());
		profile.setBlocked(false);
		
		profileRepository.save(profile);
		
		publishProfileCanceled(profileDTO.getUsername(), "An error occurred while creating profile in story service.");
	}
	
	private void publishProfileCanceled(String username, String reason) {
		ProfileCanceledEvent event = new ProfileCanceledEvent(UUID.randomUUID().toString(), username,reason);     
        publisher.publishEvent(event);
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
	@Transactional
	public void blockProfile(String username) {
		Profile profile = profileRepository.getProfileByUsername(username);
		profile.setBlocked(true);
		profileRepository.save(profile);
	}

	@Override
	public ProfileDTO findByUsername(String username) {
		Profile profile = profileRepository.getProfileByUsername(username);
		return new ProfileDTO(profile.getUsername(), profile.isPublic(), profile.isBlocked());
	}

}
