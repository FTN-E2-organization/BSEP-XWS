package app.authservice.event;

import app.authservice.model.Profile;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProfileEvent {

	private String transactionId;
	private String oldUsername;
	private Profile profile;
	private ProfileEventType type;
}
