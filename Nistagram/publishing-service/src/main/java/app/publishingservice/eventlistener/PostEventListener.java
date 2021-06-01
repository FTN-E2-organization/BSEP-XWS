package app.publishingservice.eventlistener;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import app.publishingservice.event.PostCreatedEvent;
import app.publishingservice.util.Converter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class PostEventListener {

	private final RabbitTemplate rabbitTemplate;
    private final Converter converter;
	private final String fanoutPostCreated;

	public PostEventListener(RabbitTemplate rabbitTemplate, Converter converter, @Value("${fanout.post-created}") String fanoutPostCreated) {
        this.rabbitTemplate = rabbitTemplate;
        this.converter = converter;
        this.fanoutPostCreated = fanoutPostCreated;
        
    }
	
	@Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCreatedEvent(PostCreatedEvent event) {
    	
        log.debug("Sending post created event to {}, event: {}", fanoutPostCreated, event);
     
        rabbitTemplate.convertAndSend(fanoutPostCreated,"", converter.toJSON(event));   
    }
}
