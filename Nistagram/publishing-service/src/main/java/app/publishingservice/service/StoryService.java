package app.publishingservice.service;

import app.publishingservice.dto.AddStoryDTO;

public interface StoryService {

	void create(AddStoryDTO storyDTO);
	
}
