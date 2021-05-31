package app.publishingservice.eventhandler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import app.publishingservice.dto.ProfileDTO;
import app.publishingservice.event.ProfileCreatedEvent;
import app.publishingservice.service.ProfileService;
import app.publishingservice.util.*;


@AllArgsConstructor
@Log4j2
@Component
public class ProfileCreatedHandler {

	private final ProfileService profileService;
	private final Converter converter;
	private final TransactionIdHolder transactionIdHolder;
	
	
	@RabbitListener(queues = {"${queue.profile-created}"})
    public void onOrderCreate(@Payload String payload) {
    	
		log.debug("Handling a created profile event {}", payload);
        
        ProfileCreatedEvent event = converter.toObject(payload, ProfileCreatedEvent.class);
        
        transactionIdHolder.setCurrentTransactionId(event.getTransactionId());
        
        try {
        	profileService.create(new ProfileDTO(event.getProfile().getUsername(), event.getProfile().isPublic(), 
        	event.getProfile().isAllowedTagging(), event.getProfile().isDeleted()));
        	
        } catch (Exception e) {
            log.error("Cannot create profile, reason: {}", e.getMessage());
        }
    }
	
}
