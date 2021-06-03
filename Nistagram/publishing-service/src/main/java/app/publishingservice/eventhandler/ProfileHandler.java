package app.publishingservice.eventhandler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import app.publishingservice.dto.ProfileDTO;
import app.publishingservice.event.ProfileEvent;
import app.publishingservice.event.ProfileEventType;
import app.publishingservice.service.ProfileService;
import app.publishingservice.util.*;


@AllArgsConstructor
@Log4j2
@Component
public class ProfileHandler {

	private final ProfileService profileService;
	private final Converter converter;
	private final TransactionIdHolder transactionIdHolder;
	
	
	@RabbitListener(queues = {"${queue.auth-publishing-profile}"})
    public void onProfileCreateOrUpdate(@Payload String payload) {
		    	
		log.debug("Handling a created/updated profile event {}", payload);
        
        ProfileEvent event = converter.toObject(payload, ProfileEvent.class);
        
        transactionIdHolder.setCurrentTransactionId(event.getTransactionId());
        
        try {
        	if(event.getType() == ProfileEventType.created) {
        		System.out.println("Creating profile...");
        		profileService.create(new ProfileDTO(event.getProfile().getUsername(), event.getProfile().isPublic(), 
        	    event.getProfile().isAllowedTagging(), event.getProfile().isDeleted()));
        	}
        	else if(event.getType() == ProfileEventType.updated) {
        		System.out.println("Updating profile...");
        		profileService.update(event.getOldUsername(), new ProfileDTO(event.getProfile().getUsername(), event.getProfile().isPublic(), 
        	    event.getProfile().isAllowedTagging(), event.getProfile().isDeleted()));
        	}
        } catch (Exception e) {
            log.error("Cannot create create/update, reason: {}", e.getMessage());
        }
    }
	
}
