package app.followingservice.eventhandler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import app.followingservice.dto.ProfileDTO;
import app.followingservice.event.ProfileEvent;
import app.followingservice.event.ProfileEventType;
import app.followingservice.service.ProfileService;
import app.followingservice.util.*;


@AllArgsConstructor
@Log4j2
@Component
public class ProfileHandler {

	private final ProfileService profileService;
	private final Converter converter;
	private final TransactionIdHolder transactionIdHolder;
	
	
	@RabbitListener(queues = {"${queue.auth-following-profile}"})
    public void onProfileEvent(@Payload String payload) {
    	        
        log.debug("Handling a created/updated profile event {}", payload);
        
        ProfileEvent event = converter.toObject(payload, ProfileEvent.class);
        
        transactionIdHolder.setCurrentTransactionId(event.getTransactionId());
        
        try {
        	if(event.getType() == ProfileEventType.create) {
        		System.out.println("Creating profile...");
        		profileService.addProfile(new ProfileDTO(event.getProfileDTO().username, event.getProfileDTO().isPublic));
        	}
        	else if(event.getType() == ProfileEventType.updatePersonalData) {
        		System.out.println("Updating personal data...");
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
