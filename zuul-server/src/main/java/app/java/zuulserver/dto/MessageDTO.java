package app.java.zuulserver.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

	public LocalDateTime timestamp;
	public String text;		
	public String oneTimeContentPath;
	public boolean isSeenOneTimeContent;
	public String requestType;
	public String senderUsername;
	public String receiverUsername;
}
