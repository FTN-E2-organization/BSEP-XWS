package app.activityservice.event;

import app.activityservice.dto.PublishProfileDTO;
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
public class ProfileEvent {

	private String transactionId;
	private String oldUsername;
	private PublishProfileDTO profileDTO;
	private ProfileEventType type;
}
