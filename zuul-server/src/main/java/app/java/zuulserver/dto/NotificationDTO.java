package app.java.zuulserver.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {

	public LocalDateTime timestamp;
	public String description;		
	public String contentLink;	
	public String notificationType;	
	public String wantedUsername;
	public String receiverUsername;
	
}
