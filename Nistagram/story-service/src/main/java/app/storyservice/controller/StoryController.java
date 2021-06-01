package app.storyservice.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.storyservice.dto.AddStoryDTO;
import app.storyservice.model.Profile;
import app.storyservice.model.Story;
import app.storyservice.service.StoryService;

@RestController
@RequestMapping(value = "api/story")
public class StoryController {

	private StoryService storyService;
	
	@Autowired
	public StoryController(StoryService storyService) {
		this.storyService = storyService;
	}
	
	@GetMapping("/one/{id}")
	public ResponseEntity<?> findStoryById(@PathVariable Long id){
		
		try {
			Story story = storyService.getStoryById(id);
			return new ResponseEntity<Story>(story, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/all/profile/{username}")
	public ResponseEntity<?> findAllStoriesByProfileUsername(@PathVariable String username){
		
		try {
			Collection<Story> stories = storyService.getStoriesByProfileUsername(username);
			return new ResponseEntity<Collection<Story>>(stories, HttpStatus.OK);
		}
		catch(Exception exception) {
			exception.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody AddStoryDTO storyDTO){
		try {
			//username trenutnog profila uzmi iz tokena
			
			storyService.create(storyDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
}

