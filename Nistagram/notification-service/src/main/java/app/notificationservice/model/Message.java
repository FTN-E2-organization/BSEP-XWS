package app.notificationservice.model;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;
import app.notificationservice.enums.RequestType;


@Document
public class Message {

	@Id
	private String idMongo;
	
	@Indexed(unique = true)
	private Long id;
	
	private LocalDateTime timestamp;
	
	private String text;
	
	private RequestType requestType;
	
	@JsonProperty
	private boolean isOneTimeContent;
	
	@JsonProperty
	private boolean isSeen;
	
	@DBRef
	private Profile sender;
	
	@DBRef
	private Profile receiver;

	public String getIdMongo() {
		return idMongo;
	}

	public void setIdMongo(String idMongo) {
		this.idMongo = idMongo;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	public boolean isOneTimeContent() {
		return isOneTimeContent;
	}

	public void setOneTimeContent(boolean isOneTimeContent) {
		this.isOneTimeContent = isOneTimeContent;
	}

	public boolean isSeen() {
		return isSeen;
	}

	public void setSeen(boolean isSeen) {
		this.isSeen = isSeen;
	}

	public Profile getSender() {
		return sender;
	}

	public void setSender(Profile sender) {
		this.sender = sender;
	}

	public Profile getReceiver() {
		return receiver;
	}

	public void setReceiver(Profile receiver) {
		this.receiver = receiver;
	}

	@Override
	public int hashCode() {
		return idMongo.hashCode();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
