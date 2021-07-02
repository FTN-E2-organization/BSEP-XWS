package app.notificationservice.repository;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;
import app.notificationservice.model.Message;
import app.notificationservice.model.Profile;

public interface MessageRepository extends MongoRepository<Message, String> {

	Collection<Message> getMessageByReceiverAndSender(Profile receiver, Profile sender);
	Collection<Message> getMessageByReceiver(Profile receiver);
	Collection<Message> getMessageBySender(Profile receiver);
}
