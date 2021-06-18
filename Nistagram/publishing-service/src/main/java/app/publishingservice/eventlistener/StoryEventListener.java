package app.publishingservice.eventlistener;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import app.publishingservice.event.StoryEvent;
import app.publishingservice.util.Converter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class StoryEventListener {

	private final RabbitTemplate rabbitTemplate;
    private final Converter converter;
	private final String queueStory;

	public StoryEventListener(RabbitTemplate rabbitTemplate, Converter converter, @Value("${queue.story}") String queueStory) {
        this.rabbitTemplate = rabbitTemplate;
        this.converter = converter;
        this.queueStory = queueStory;
        
    }
	
	@Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onStoryEvent(StoryEvent event) {
		    	
        log.debug("Sending story event to {}, event: {}", queueStory, event);
     
        rabbitTemplate.convertAndSend(queueStory, converter.toJSON(event));      
    }

}
