package app.notificationservice.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import app.notificationservice.enums.RequestType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Message {

	@Id
	private String idMongo;
	
	private LocalDateTime timestamp;
	
	private String text;
	
	private RequestType requestType;
	
	@DBRef
	private OneTimeContent oneTimeContent;
	
	@DBRef
	private Profile sender;
	
	@DBRef
	private Profile receiver;
}
