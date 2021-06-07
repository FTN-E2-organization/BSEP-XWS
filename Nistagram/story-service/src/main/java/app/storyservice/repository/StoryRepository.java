package app.storyservice.repository;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.storyservice.dto.StoryDTO;
import app.storyservice.model.Profile;
import app.storyservice.model.Story;

@Repository
public interface StoryRepository extends MongoRepository<Story, String> {

	Story getStoryById(Long id); // da li ga trazi po anotaciji Id tj.idMongo ili polju id

	Collection<Story> getStoryByProfile(Profile profile);

	Set<Story> getStoryByProfileIn(Set<Profile> storyProfiles);
}