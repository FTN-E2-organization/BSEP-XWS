package app.notificationservice.eventhandler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import app.notificationservice.dto.ProfileDTO;
import app.notificationservice.event.ProfileEvent;
import app.notificationservice.event.ProfileEventType;
import app.notificationservice.service.ProfileService;
import app.notificationservice.util.*;



@AllArgsConstructor
@Log4j2
@Component
public class ProfileHandler {

	private final ProfileService profileService;
	private final Converter converter;
	private final TransactionIdHolder transactionIdHolder;
	
	
	@RabbitListener(queues = {"${queue.auth-notification-profile}"})
    public void onProfileCreate(@Payload String payload) {
    
        log.debug("Handling a created/updated profile event {}", payload);
        
        ProfileEvent event = converter.toObject(payload, ProfileEvent.class);
        
        transactionIdHolder.setCurrentTransactionId(event.getTransactionId());
        
        try {
        	if(event.getType() == ProfileEventType.create) {
        		System.out.println("Creating profile...");
            	profileService.create(new ProfileDTO(event.getProfileDTO().username));
        	}
        	else if(event.getType() == ProfileEventType.updatePersonalData) {
        		System.out.println("Updating personal data...");
        		profileService.updatePersonalData(event.getOldUsername(), new ProfileDTO(event.getProfileDTO().username));
        	}
        	else if(event.getType() == ProfileEventType.updateProfilePrivacy) {
        		System.out.println("Do nothing...");
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
