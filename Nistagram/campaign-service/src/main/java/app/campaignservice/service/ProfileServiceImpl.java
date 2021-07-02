package app.campaignservice.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.campaignservice.event.ProfileCanceledEvent;
import app.campaignservice.dto.ProfileDTO;
import app.campaignservice.model.Profile;
import app.campaignservice.repository.ProfileRepository;

@Service
public class ProfileServiceImpl implements ProfileService{

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
		profile.setInfluencer(profileDTO.isInfluencer);
		profile.setBlocked(false);
		
		profileRepository.save(profile);
		
		publishProfileCanceled(profileDTO.username, "An error occurred while creating profile in campaign service.");
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
		return new ProfileDTO(profile.getUsername(), profile.isPublic(), profile.isInfluencer(), profile.isBlocked());
	}

	@Override
	public Long getIdByUsername(String ownerUsername) {
		return profileRepository.findByUsername(ownerUsername).getId();
	}

	@Override
	@Transactional
	public void deleteByUsername(String username) {
		Profile profile = profileRepository.findByUsername(username);
		if(profile != null)
			profileRepository.delete(profile);
	}

	@Override
	@Transactional
	public void createInfluencer(String username) {
		Profile profile = profileRepository.findByUsername(username);	
		profile.setInfluencer(true);	
		profileRepository.save(profile);
	}
}
