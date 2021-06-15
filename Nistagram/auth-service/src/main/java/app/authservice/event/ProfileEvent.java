package app.authservice.event;

import app.authservice.dto.PublishProfileDTO;
import lombok.*;

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
