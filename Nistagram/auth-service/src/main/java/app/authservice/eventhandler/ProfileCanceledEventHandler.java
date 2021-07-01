package app.authservice.eventhandler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import app.authservice.event.ProfileCanceledEvent;
import app.authservice.service.ProfileService;
import app.authservice.util.Converter;


@Log4j2
@Component
@AllArgsConstructor
public class ProfileCanceledEventHandler {

	private final ProfileService profileService;
	private final Converter converter;
	
	
	@RabbitListener(queues = {"${queue.profile-canceled}"})
    public void onProfileCanceled(@Payload String payload) {
    	
        log.debug("Handling a canceled profile event {}", payload);
        
        ProfileCanceledEvent event = converter.toObject(payload, ProfileCanceledEvent.class);
        
        System.out.println("Receiving profile canceled event with reason " + event.getReason());
        
        //delete profile
    }
	
    
}
