package app.publishingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.publishingservice.dto.AddStoryDTO;
import app.publishingservice.service.StoryService;

@RestController
@RequestMapping(value = "api/story")
public class StoryController {
	
	private StoryService storyService;
	
	@Autowired
	public StoryController(StoryService storyService) {
		this.storyService = storyService;
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody AddStoryDTO storyDTO){
		try {
			storyService.create(storyDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
