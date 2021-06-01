package app.publishingservice.eventlistener;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import app.publishingservice.event.StoryCreatedEvent;
import app.publishingservice.util.Converter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class StoryEventListener {

	private final RabbitTemplate rabbitTemplate;
    private final Converter converter;
	private final String fanoutStoryCreated;

	public StoryEventListener(RabbitTemplate rabbitTemplate, Converter converter, @Value("${fanout.story-created}") String fanoutStoryCreated) {
        this.rabbitTemplate = rabbitTemplate;
        this.converter = converter;
        this.fanoutStoryCreated = fanoutStoryCreated;
        
    }
	
	@Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCreatedEvent(StoryCreatedEvent event) {
		    	
        log.debug("Sending story created event to {}, event: {}", fanoutStoryCreated, event);
     
        rabbitTemplate.convertAndSend(fanoutStoryCreated,"", converter.toJSON(event));   
        
        
    }
}
