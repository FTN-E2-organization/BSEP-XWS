package app.followingservice.event;

import app.followingservice.model.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProfileCreatedEvent {

	private String transactionId;
	private Profile profile;
}