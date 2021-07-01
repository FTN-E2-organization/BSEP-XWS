package app.authservice.eventlistener;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import app.authservice.event.ProfileEvent;
import app.authservice.util.Converter;

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
    public void onProfileEvent(ProfileEvent event) {    	        
        System.out.println("Sending profile " + event.getType() + " event to services...");
  
        rabbitTemplate.convertAndSend(fanout,"", converter.toJSON(event));   
    }

}
