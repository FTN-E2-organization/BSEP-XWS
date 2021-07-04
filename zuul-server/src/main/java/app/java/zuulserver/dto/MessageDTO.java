package app.java.zuulserver.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

	public String idMongo;
	public Long id;
	public LocalDateTime timestamp;
	public String text;		
	public boolean isOneTimeContent;
	public boolean isSeenOneTimeContent;
	public String requestType;
	public String senderUsername;
	public String receiverUsername;
}
