package app.publishingservice.service;

import java.util.Collection;

import app.publishingservice.dto.StoryDTO;
import app.publishingservice.model.Story;

public interface StoryService {

	void create(StoryDTO storyDTO);

	Collection<Story> getHighlightStoriesByUsername(String username);
	
}
