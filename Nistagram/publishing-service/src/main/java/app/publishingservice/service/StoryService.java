package app.publishingservice.service;

import java.util.Collection;

import app.publishingservice.dto.StoryDTO;
import app.publishingservice.model.Story;

public interface StoryService {

	Long create(StoryDTO storyDTO);
	Collection<Story> getHighlightStoriesByUsername(String username);
	Story getById(long storyId) throws Exception;
	void delete(Long storyId);
}
