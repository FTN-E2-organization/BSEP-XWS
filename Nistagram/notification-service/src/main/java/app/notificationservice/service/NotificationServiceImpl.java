package app.notificationservice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.notificationservice.dto.NotificationDTO;
import app.notificationservice.enums.NotificationType;
import app.notificationservice.repository.NotificationRepository;
import app.notificationservice.repository.ProfileRepository;
import app.notificationservice.model.Profile;
import app.notificationservice.model.Notification;

@Service
public class NotificationServiceImpl implements NotificationService {

	private NotificationRepository notificationRepository;
	private ProfileRepository profileRepository;
	
	@Autowired
	public NotificationServiceImpl(NotificationRepository notificationRepository, ProfileRepository profileRepository) {
		this.notificationRepository = notificationRepository;
		this.profileRepository = profileRepository;
	}

	@Override
	public void create(NotificationDTO notificationDTO) throws Exception {
		System.out.println(notificationDTO.wantedUsername + " - receiver: " + notificationDTO.receiverUsername);
		
		Profile wantedUser = profileRepository.findProfileByUsername(notificationDTO.wantedUsername);
		Profile receiver = profileRepository.findProfileByUsername(notificationDTO.receiverUsername);	
		
		if (wantedUser == null) {
			throw new Exception("Username does not exist.");
		}
		if (receiver == null) {
			throw new Exception("Receiver username does not exist.");
		}
		
		Notification notification = new Notification();	
		notification.setTimestamp(LocalDateTime.now());
		notification.setDescription(notificationDTO.description);
		notification.setContentLink(notificationDTO.contentLink);
		notification.setNotificationType(NotificationType.valueOf(notificationDTO.notificationType));
		notification.setWantedUser(wantedUser);
		notification.setReceiver(receiver);		
		notificationRepository.save(notification);
	}

	@Override
	public Collection<NotificationDTO> getNotificationsByProfileUsername(String username) {
		Profile profile = profileRepository.findProfileByUsername(username);
		Collection<Notification> notifications = notificationRepository.getNotificationByReceiver(profile);
		Collection<NotificationDTO> notificationDTOs = new ArrayList<>();
		if (notifications != null) {
			for(Notification n : notifications) {
				NotificationDTO dto = new NotificationDTO();
				dto.timestamp = n.getTimestamp();
				dto.description = n.getDescription();
				dto.contentLink = n.getContentLink();
				dto.notificationType = n.getNotificationType().toString();
				dto.wantedUsername = n.getWantedUser().getUsername();
				dto.receiverUsername = n.getReceiver().getUsername();
				notificationDTOs.add(dto);
			}
		}
		return notificationDTOs;
	}		
		
	
}
