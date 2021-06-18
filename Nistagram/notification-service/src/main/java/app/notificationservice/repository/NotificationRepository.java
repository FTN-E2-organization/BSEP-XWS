package app.notificationservice.repository;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.notificationservice.model.Notification;
import app.notificationservice.model.Profile;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

	Collection<Notification> getNotificationByReceiver(Profile receiver);
	
}
