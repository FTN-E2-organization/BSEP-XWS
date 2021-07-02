package app.notificationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
