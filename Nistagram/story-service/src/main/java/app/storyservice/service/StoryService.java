package app.storyservice.service;

import java.util.Collection;

import app.storyservice.dto.AddStoryDTO;
import app.storyservice.model.Profile;
import app.storyservice.model.Story;

public interface StoryService {
	
	Story getStoryById(Long id);
	Collection<Story> getStoriesByProfileUsername(String username);
	void create(AddStoryDTO storyDTO);
	
}
