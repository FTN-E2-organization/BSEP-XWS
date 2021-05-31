package app.authservice.eventlistener;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import app.authservice.event.ProfileCreatedEvent;
import app.authservice.util.Converter;

@Log4j2
@Component
public class ProfileEventListener {

	private final RabbitTemplate rabbitTemplate;
    private final Converter converter;
    private final String queueProfileCreated;

    public ProfileEventListener(RabbitTemplate rabbitTemplate, Converter converter, @Value("${queue.profile-create}") String queueProfileCreated) {
        this.rabbitTemplate = rabbitTemplate;
        this.converter = converter;
        this.queueProfileCreated = queueProfileCreated;
        
    }
    
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCreateEvent(ProfileCreatedEvent event) {
    	
        log.debug("Sending profile created event to {}, event: {}", queueProfileCreated, event);
        
        rabbitTemplate.convertAndSend(queueProfileCreated, converter.toJSON(event));
        
    }

}
