package app.storyservice.eventhandler;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import app.storyservice.dto.StoryDTO;
import app.storyservice.event.StoryEvent;
import app.storyservice.event.StoryEventType;
import app.storyservice.service.StoryService;
import app.storyservice.util.Converter;
import app.storyservice.util.TransactionIdHolder;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Log4j2
@Component
public class StoryHandler {

	private final StoryService storyService;
	private final Converter converter;
	private final TransactionIdHolder transactionIdHolder;
	
	@RabbitListener(queues = {"${queue.story}"})
    public void onStoryCreate(@Payload String payload) {
    	
		log.debug("Handling a story event {}", payload);
        
        StoryEvent event = converter.toObject(payload, StoryEvent.class);
        
        transactionIdHolder.setCurrentTransactionId(event.getTransactionId());
        
        try {
        	if(event.getType() == StoryEventType.created)
	        	storyService.create(new StoryDTO(event.getStoryId(), event.isDeleted(), event.getTimestamp(), 
	        			event.getLocation(), event.getDescription(), event.isForCloseFriends(),
	        			event.getHashtags(), event.getTagged() ,event.getOwnerUsername()));
        	
        	else if(event.getType() == StoryEventType.deleted)
        		storyService.delete(event.getStoryId());
        	
        } catch (Exception e) {
            log.error("Cannot create/delete story, reason: {}", e.getMessage());
        }
    }
	
}
