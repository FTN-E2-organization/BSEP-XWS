package app.notificationservice.mapper;

import java.util.ArrayList;
import java.util.Collection;

import app.notificationservice.dto.MessageDTO;
import app.notificationservice.model.Message;

public class MessageMapper {

	public static Collection<MessageDTO> toMessageDTOs(Collection<Message> messages){
		Collection<MessageDTO> messageDTOs = new ArrayList<>();
		
		for(Message message: messages) {
			MessageDTO messageDTO = new MessageDTO();
			
			messageDTO.requestType = message.getRequestType().toString();
			messageDTO.text = message.getText();
			messageDTO.timestamp = message.getTimestamp();
			messageDTO.receiverUsername = message.getReceiver().getUsername();
			messageDTO.senderUsername = message.getSender().getUsername();
			if(message.getOneTimeContent() != null) {
				messageDTO.oneTimeContentPath = message.getOneTimeContent().getContentPath();
				messageDTO.isSeenOneTimeContent = message.getOneTimeContent().isSeen();
			}
			messageDTOs.add(messageDTO);
		}
		
		return messageDTOs;
	}
}
