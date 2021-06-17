package app.notificationservice.service;

import java.time.LocalDateTime;

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
	public void create(NotificationDTO notificationDTO) {
		Profile wantedUser = profileRepository.getProfileByUsername(notificationDTO.wantedUsername);
		Profile receiver = profileRepository.getProfileByUsername(notificationDTO.receiverUsername);	
		
		Notification notification = new Notification();
	
		notification.setTimestamp(LocalDateTime.now());
		notification.setDescription(notificationDTO.description);
		notification.setContentLink(notificationDTO.contentLink);
		notification.setNotificationType(NotificationType.valueOf(notificationDTO.notificationType));
		notification.setWantedUser(wantedUser);
		notification.setReceiver(receiver);
		
		notificationRepository.save(notification);
	}
	
}
