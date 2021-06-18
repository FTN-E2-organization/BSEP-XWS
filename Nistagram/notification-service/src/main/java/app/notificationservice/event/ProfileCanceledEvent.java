package app.notificationservice.event;

import app.notificationservice.model.Profile;
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
public class ProfileCanceledEvent {

	private String transactionId;
	private Profile profile;
}
