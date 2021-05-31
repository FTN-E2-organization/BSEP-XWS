package app.publishingservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.publishingservice.dto.ProfileDTO;
import app.publishingservice.enums.ProfileStatus;
import app.publishingservice.event.ProfileCreatedEvent;
import app.publishingservice.model.Profile;
import app.publishingservice.repository.ProfileRepository;
import app.publishingservice.util.TransactionIdHolder;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ProfileServiceImpl implements ProfileService {
	
	private ProfileRepository profileRepository;
	private final ApplicationEventPublisher publisher;
	private final TransactionIdHolder transactionIdHolder;
	
	@Autowired
	public ProfileServiceImpl(ProfileRepository profileRepository, TransactionIdHolder transactionIdHolder, ApplicationEventPublisher publisher) {
		this.profileRepository = profileRepository;
		this.publisher = publisher;
		this.transactionIdHolder = transactionIdHolder;
	}

	@Override
	@Transactional
	public void create(ProfileDTO profileDTO) {
		log.debug("Start creating profile {}", profileDTO.username);
		
		Profile profile = new Profile();
		
		profile.setUsername(profileDTO.username);
		profile.setPublic(profileDTO.isPublic);
		profile.setAllowedTagging(profileDTO.allowedTagging);
		profile.setDeleted(false);
		profile.setStatus(ProfileStatus.created);
		
		profileRepository.save(profile);
		
		publishProfileCreated(profile);
	}
	
	private void publishProfileCreated(Profile profile) {
    	
        ProfileCreatedEvent event = new ProfileCreatedEvent(transactionIdHolder.getCurrentTransactionId(), profile);
        
        log.debug("Publishing profile created event {}", event);
        
        publisher.publishEvent(event);
        
    }
	
	@Override
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

	@Override
	public void cancel(String username) {
		Profile profile = profileRepository.findByUsername(username);
		profile.setStatus(ProfileStatus.canceled);
		profileRepository.save(profile);
		
	}

}
