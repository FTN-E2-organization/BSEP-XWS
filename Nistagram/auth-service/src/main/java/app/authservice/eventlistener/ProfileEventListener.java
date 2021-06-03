package app.authservice.eventlistener;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import app.authservice.event.ProfileEvent;
import app.authservice.util.Converter;

@Log4j2
@Component
public class ProfileEventListener {

	private final RabbitTemplate rabbitTemplate;
    private final Converter converter;
    private final String fanout;

    public ProfileEventListener(RabbitTemplate rabbitTemplate, Converter converter, @Value("${fanout.auth}") String fanout) {
        this.rabbitTemplate = rabbitTemplate;
        this.converter = converter;
        this.fanout = fanout;
        
    }
    
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCreatedEvent(ProfileEvent event) {
    	
        log.debug("Sending profile created event to {}, event: {}", fanout, event);
        
        System.out.println("poruka poslana");
     
        rabbitTemplate.convertAndSend(fanout,"", converter.toJSON(event));   
    }

}
