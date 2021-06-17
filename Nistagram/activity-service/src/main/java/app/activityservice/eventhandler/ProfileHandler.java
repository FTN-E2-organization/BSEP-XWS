package app.activityservice.eventhandler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import app.activityservice.dto.ProfileDTO;
import app.activityservice.event.ProfileEvent;
import app.activityservice.event.ProfileEventType;
import app.activityservice.service.ProfileService;
import app.activityservice.util.*;



@AllArgsConstructor
@Log4j2
@Component
public class ProfileHandler {

	private final ProfileService profileService;
	private final Converter converter;
	private final TransactionIdHolder transactionIdHolder;
	
	
	@RabbitListener(queues = {"${queue.auth-activity-profile}"})
    public void onProfileCreate(@Payload String payload) {
    
        log.debug("Handling a created/updated profile event {}", payload);
        
        ProfileEvent event = converter.toObject(payload, ProfileEvent.class);
        
        transactionIdHolder.setCurrentTransactionId(event.getTransactionId());
        
        try {
        	if(event.getType() == ProfileEventType.create) {
        		System.out.println("Creating profile...");
        		System.out.println("isDeleted=" + event.getProfileDTO().isBlocked);
            	profileService.create(new ProfileDTO(event.getProfileDTO().username, event.getProfileDTO().allowedTagging, event.getProfileDTO().isBlocked));
        	}
        	else if(event.getType() == ProfileEventType.updatePersonalData) {
        		System.out.println("Updating personal data...");
        		profileService.updatePersonalData(event.getOldUsername(), new ProfileDTO(event.getProfileDTO().username));
        	}
        	else if(event.getType() == ProfileEventType.updateProfilePrivacy) {
        		System.out.println("Updating profile privacy...");
        		profileService.updateProfilePrivacy(new ProfileDTO(event.getProfileDTO().username, event.getProfileDTO().allowedTagging));
        	}
        	else if(event.getType() == ProfileEventType.block) {
        		System.out.println("Blocking profile...");
        		profileService.blockProfile(event.getProfileDTO().username);
        	}
        } catch (Exception e) {
            log.error("Cannot create create/update, reason: {}", e.getMessage());
        }
    }
	
}
