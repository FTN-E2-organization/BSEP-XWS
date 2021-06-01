package app.storyservice.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.storyservice.model.Profile;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {

	Profile getProfileByUsername(String username);

	Set<Profile> getProfileByUsernameIn(List<String> following);
	
}