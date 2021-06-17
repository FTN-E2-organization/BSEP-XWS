package app.storyservice.eventhandler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import app.storyservice.dto.ProfileDTO;
import app.storyservice.event.ProfileEvent;
import app.storyservice.event.ProfileEventType;
import app.storyservice.service.ProfileService;
import app.storyservice.util.*;


@AllArgsConstructor
@Log4j2
@Component
public class ProfileHandler {

	private final ProfileService profileService;
	private final Converter converter;
	private final TransactionIdHolder transactionIdHolder;
	
	
	@RabbitListener(queues = {"${queue.auth-story-profile}"})
    public void onProfileEvent(@Payload String payload) {
		 
        log.debug("Handling a created/updated profile event {}", payload);
        
        ProfileEvent event = converter.toObject(payload, ProfileEvent.class);
        
        transactionIdHolder.setCurrentTransactionId(event.getTransactionId());
        
        try {
        	if(event.getType() == ProfileEventType.create) {
        		System.out.println("Creating profile...");
            	profileService.create(new ProfileDTO(event.getProfileDTO().username, event.getProfileDTO().isPublic, event.getProfileDTO().isBlocked));
        	}
        	else if(event.getType() == ProfileEventType.updatePersonalData) {
        		System.out.println("Updating profile personal data...");
        		profileService.updatePersonalData(event.getOldUsername(), new ProfileDTO(event.getProfileDTO().username));
        	}
        	else if(event.getType() == ProfileEventType.updateProfilePrivacy) {
        		System.out.println("Updating profile privacy...");
        		profileService.updateProfilePrivacy(new ProfileDTO(event.getProfileDTO().username, event.getProfileDTO().isPublic));
        	}
        } catch (Exception e) {
            log.error("Cannot create create/update, reason: {}", e.getMessage());
        }
    }
	
}
