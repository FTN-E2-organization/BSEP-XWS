package app.publishingservice.eventlistener;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import app.publishingservice.event.ProfileCreatedEvent;
import app.publishingservice.util.Converter;


public class ProfileEventListener {

	/*private static final String ROUTING_KEY = "";
    
    private final RabbitTemplate rabbitTemplate;
    private final Converter converter;
    private final String queueProfileCreateName;

    public ProfileEventListener(RabbitTemplate rabbitTemplate, Converter converter, @Value("${queue.profile-create}") String queueProfileCreateName) {
        this.rabbitTemplate = rabbitTemplate;
        this.converter = converter;
        this.queueProfileCreateName = queueProfileCreateName;
        
    }
    
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCreateEvent(ProfileCreatedEvent event) {
    	
        log.debug("Sending profile created event to {}, event: {}", queueProfileCreateName, event);
        
        rabbitTemplate.convertAndSend(queueProfileCreateName, converter.toJSON(event));
        
    }*/
	
}
