package app.publishingservice.service;

import java.util.Collection;

import app.publishingservice.dto.AddStoryDTO;
import app.publishingservice.model.Story;

public interface StoryService {

	void create(AddStoryDTO storyDTO);

	Collection<Story> getHighlightStoriesByUsername(String username);
	
}
