package app.notificationservice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.notificationservice.dto.MessageDTO;
import app.notificationservice.enums.RequestType;
import app.notificationservice.mapper.MessageMapper;
import app.notificationservice.model.Message;
import app.notificationservice.model.OneTimeContent;
import app.notificationservice.model.Profile;
import app.notificationservice.repository.MessageRepository;
import app.notificationservice.repository.ProfileRepository;


@Service
public class MessageServiceImpl implements MessageService {

	private MessageRepository messageRepository;
	private ProfileRepository profileRepository;
	
	@Autowired
	public MessageServiceImpl(MessageRepository messageRepository, ProfileRepository profileRepository) {
		this.messageRepository = messageRepository;
		this.profileRepository = profileRepository;
	}

	@Override
	public void sendTextMessage(MessageDTO messageDTO) throws Exception{
		Profile sender = profileRepository.findProfileByUsername(messageDTO.senderUsername);
		Profile receiver = profileRepository.findProfileByUsername(messageDTO.receiverUsername);	
		Message message = new Message();
		
		if (sender == null || receiver == null) {
			throw new Exception();
		}
		
		message.setRequestType(RequestType.valueOf(messageDTO.requestType));
		message.setText(messageDTO.text);
		message.setTimestamp(LocalDateTime.now());
		message.setSender(sender);
		message.setReceiver(receiver);
		
		/*if(messageDTO.oneTimeContentPath != null && !messageDTO.oneTimeContentPath.isEmpty()) {
			OneTimeContent oneTimeContent = new OneTimeContent();
			oneTimeContent.setContentPath(messageDTO.oneTimeContentPath);
			oneTimeContent.setSeen(messageDTO.isSeenOneTimeContent);
			//prije setovanja sacuvati one time content u njegovu bazu
			message.setOneTimeContent(oneTimeContent);
		}*/
		
		messageRepository.save(message);
	}

	@Override
	public Collection<MessageDTO> getChatBetweenTwoProfiles(String firstUsername, String secondUsername) {
		Profile firstProfile = profileRepository.findProfileByUsername(firstUsername);
		Profile secondProfile = profileRepository.findProfileByUsername(secondUsername);
		Collection<Message> chat = new ArrayList<>();
		Collection<Message> chatWithoutMessageRequests = new ArrayList<>();
		
		chat.addAll(messageRepository.getMessageByReceiverAndSender(firstProfile, secondProfile));
		chat.addAll(messageRepository.getMessageByReceiverAndSender(secondProfile, firstProfile));
		
		for(Message message:chat) {
			if(message.getRequestType() == RequestType.approved) {
				chatWithoutMessageRequests.add(message);
			}
		}
		
		List<Message> chatList = new ArrayList<>(chatWithoutMessageRequests);
		chatList.sort((o1,o2) -> o1.getTimestamp().compareTo(o2.getTimestamp()));
		
		return MessageMapper.toMessageDTOs(chatList);
	}

	@Override
	public Collection<String> getUsernamesFromChat(String username) {
		Profile profile = profileRepository.findProfileByUsername(username);
		Collection<Message> receivedMessages = messageRepository.getMessageByReceiver(profile);
		Collection<Message> sentMessages = messageRepository.getMessageBySender(profile);
		Collection<String> usernames = new ArrayList<>();
		
		for(Message message:receivedMessages) {
			if(!usernames.contains(message.getSender().getUsername()) && message.getRequestType() == RequestType.approved)
				usernames.add(message.getSender().getUsername());
		}
		
		for(Message message:sentMessages) {
			if(!usernames.contains(message.getReceiver().getUsername()) && message.getRequestType() == RequestType.approved)
				usernames.add(message.getReceiver().getUsername());
		}
		
		return usernames;
	}

	@Override
	public Collection<MessageDTO> getMessageRequestsByUsername(String username) {
		Collection<Message> receivedMessages = messageRepository.getMessageByReceiver(profileRepository.findProfileByUsername(username));
		Collection<Message> messageRequests = new ArrayList<>();
		
		for(Message message:receivedMessages) {
			if(message.getRequestType() == RequestType.created) {
				messageRequests.add(message);
			}
		}
		
		return MessageMapper.toMessageDTOs(messageRequests);
	}
}
