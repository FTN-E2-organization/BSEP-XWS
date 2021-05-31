package app.authservice.event;

import app.authservice.model.Profile;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProfileCreatedEvent {

	private String transactionId;
	private Profile profile;
}
