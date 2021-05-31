package app.authservice.eventhandler;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import app.authservice.event.ProfileDoneEvent;
import app.authservice.service.ProfileService;
import app.authservice.util.Converter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@AllArgsConstructor
public class ProfileDoneEventHandler {

	private final ProfileService profileService;
	private final Converter converter;
	
	
	@RabbitListener(queues = {"${queue.profile-done}"})
    public void onProfileDone(@Payload String payload) {
    	
        log.debug("Handling a canceled profile event {}", payload);
        
        ProfileDoneEvent event = converter.toObject(payload, ProfileDoneEvent.class);
        
        profileService.done(event.getProfile().getUsername());
    }
}
