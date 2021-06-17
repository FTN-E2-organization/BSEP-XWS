package app.notificationservice.service;

import java.util.Collection;

import app.notificationservice.dto.NotificationDTO;

public interface NotificationService {

	void create(NotificationDTO notificationDTO) throws Exception;

	Collection<NotificationDTO> getNotificationsByProfileUsername(String username);

}
