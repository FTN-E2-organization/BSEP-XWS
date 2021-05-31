package app.publishingservice.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.publishingservice.dto.AddStoryDTO;
import app.publishingservice.model.Story;
import app.publishingservice.service.*;

@RestController
@RequestMapping(value = "api/story")
public class StoryController {
	
	private StoryService storyService;
	private LocationService locationService;
	private HashtagService hashtagService;
	
	@Autowired
	public StoryController(StoryService storyService, LocationService locationService, HashtagService hashtagService) {
		this.storyService = storyService;
		this.locationService = locationService;
		this.hashtagService = hashtagService;
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody AddStoryDTO storyDTO){
		try {
			/*Username trenutno ulogovanog korisnika ce se preuzeti iz tokena*/
			
			if(storyDTO.location != null && !storyDTO.location.isEmpty()) {
				locationService.createIfDoesNotExist(storyDTO.location);
			}
			
			if(storyDTO.hashtags != null && storyDTO.hashtags.size() != 0) {
				hashtagService.createIfDoesNotExist(storyDTO.hashtags);
			}
			
			storyService.create(storyDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/highlight/{username}")
	public ResponseEntity<?> getAllByUsername(@PathVariable String username){
		try {
			username = "user_1";  //ovo obrisati kad se uradi front...
			return new ResponseEntity<Collection<Story>>(storyService.getHighlightStoriesByUsername(username), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
}
