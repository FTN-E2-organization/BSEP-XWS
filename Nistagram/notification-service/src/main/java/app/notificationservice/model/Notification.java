package app.notificationservice.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import app.notificationservice.enums.NotificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Notification {
	
	@Id
	private String idMongo;
	
	private LocalDateTime timestamp;
	
	private String description;	
	
	private String contentLink;
	
	private NotificationType notificationType;

	@DBRef
	private Profile wantedUser;
	
	@DBRef
	private Profile receiver;
	
	
}
