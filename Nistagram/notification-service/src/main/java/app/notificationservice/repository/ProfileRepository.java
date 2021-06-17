package app.notificationservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.notificationservice.model.Profile;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {

	Profile getProfileByUsername(String username);
	
}
