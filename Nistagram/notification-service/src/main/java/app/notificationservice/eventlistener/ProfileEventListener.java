package app.notificationservice.eventlistener;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import app.notificationservice.event.ProfileCanceledEvent;
import app.notificationservice.util.Converter;

@Component
public class ProfileEventListener {
	
	private final RabbitTemplate rabbitTemplate;
    private final Converter converter;
    private final String queueProfileCanceled;
    
    public ProfileEventListener(RabbitTemplate rabbitTemplate, Converter converter, 
    							@Value("${queue.profile-canceled}") String queueProfileCanceled) {
        this.rabbitTemplate = rabbitTemplate;
        this.converter = converter;
        this.queueProfileCanceled = queueProfileCanceled;
    }
 
    
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void onCanceledEvent(ProfileCanceledEvent event) {
    	
    	System.out.println("Sending profile canceled event with reason " + event.getReason());
        
        rabbitTemplate.convertAndSend(queueProfileCanceled, converter.toJSON(event));
    }
	
    @Async
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void onCanceledEventBeforeCommit(ProfileCanceledEvent event) {
    	
    	System.out.println("Sending profile canceled event with reason " + event.getReason());
        
        rabbitTemplate.convertAndSend(queueProfileCanceled, converter.toJSON(event)); 
    }
}
