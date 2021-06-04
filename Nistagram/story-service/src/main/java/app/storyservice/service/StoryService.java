package app.storyservice.service;

import java.util.Collection;

import app.storyservice.dto.StoryDTO;
import app.storyservice.model.Profile;
import app.storyservice.model.Story;

public interface StoryService {
	
	Story getStoryById(Long id);
	Collection<Story> getStoriesByProfileUsername(String username);
	void create(StoryDTO storyDTO);
	
}
