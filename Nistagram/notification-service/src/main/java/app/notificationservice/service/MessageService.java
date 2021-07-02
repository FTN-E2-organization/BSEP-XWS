package app.notificationservice.service;

import java.util.Collection;
import app.notificationservice.dto.MessageDTO;

public interface MessageService {

	void sendTextMessage(MessageDTO messageDTO) throws Exception;
	Collection<MessageDTO> getChatBetweenTwoProfiles(String firstUsername, String secondUsername);
	Collection<String> getUsernamesFromChat(String username);
	Collection<MessageDTO> getMessageRequestsByUsername(String username);
}
