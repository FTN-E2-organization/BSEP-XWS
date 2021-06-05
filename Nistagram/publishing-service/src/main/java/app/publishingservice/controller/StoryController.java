package app.publishingservice.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import app.publishingservice.dto.StoryDTO;
import app.publishingservice.mapper.StoryMapper;
import app.publishingservice.service.*;

@RestController
@RequestMapping(value = "api/publishing/story")
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
	public ResponseEntity<?> create(@RequestBody StoryDTO storyDTO){
		try {
			/*Username trenutno ulogovanog korisnika ce se preuzeti iz tokena*/
			storyDTO.ownerUsername = "pero123";
						
			if(storyDTO.location != null && !storyDTO.location.isEmpty()) {
				locationService.createIfDoesNotExist(storyDTO.location);
			}
			
			if(storyDTO.hashtags != null && storyDTO.hashtags.size() != 0) {
				hashtagService.createIfDoesNotExist(storyDTO.hashtags);
			}
			
			return new ResponseEntity<>(storyService.create(storyDTO), HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/highlight/{username}")
	public ResponseEntity<?> getAllByUsername(@PathVariable String username){
		try {
			return new ResponseEntity<Collection<StoryDTO>>(StoryMapper.toStoryDTOs(storyService.getHighlightStoriesByUsername(username)), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@GetMapping("/{storyId}")
	public ResponseEntity<?> getById(@PathVariable long storyId){
		try {
			return new ResponseEntity<>(StoryMapper.toStoryDTO(storyService.getById(storyId)), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}		
	
}
