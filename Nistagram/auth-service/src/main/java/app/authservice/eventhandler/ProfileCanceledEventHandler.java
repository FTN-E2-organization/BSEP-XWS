package app.authservice.eventhandler;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import app.authservice.event.ProfileCanceledEvent;
import app.authservice.service.ProfileService;
import app.authservice.util.Converter;

@Component
@AllArgsConstructor
public class ProfileCanceledEventHandler {

	private final ProfileService profileService;
	private final Converter converter;
	
	
	@RabbitListener(queues = {"${queue.profile-canceled}"})
    public void onProfileCanceled(@Payload String payload) {
        ProfileCanceledEvent event = converter.toObject(payload, ProfileCanceledEvent.class);
        
        System.out.println("Receiving profile canceled event with reason " + event.getReason());
        
        profileService.deleteProfileByUsername(event.getUsername());
    }
	
}
