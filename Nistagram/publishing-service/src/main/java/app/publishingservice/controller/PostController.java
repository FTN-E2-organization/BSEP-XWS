package app.publishingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.publishingservice.dto.AddPostDTO;
import app.publishingservice.service.HashtagService;
import app.publishingservice.service.LocationService;
import app.publishingservice.service.PostService;

@RestController
@RequestMapping(value = "api/post")
public class PostController {

	private PostService postService;
	private LocationService locationService;
	private HashtagService hashtagService;	
	
	@Autowired
	public PostController(PostService postService, LocationService locationService, HashtagService hashtagService) {
		this.postService = postService;
		this.locationService = locationService;
		this.hashtagService = hashtagService;
	}	
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody AddPostDTO postDTO){
		try {
			/*Username trenutno ulogovanog korisnika ce se preuzeti iz tokena*/
			
//			if(storyDTO.location != null && !storyDTO.location.isEmpty()) {
//				locationService.createIfDoesNotExist(storyDTO.location);
//			}
//			
//			if(storyDTO.hashtags != null && storyDTO.hashtags.size() != 0) {
//				hashtagService.createIfDoesNotExist(storyDTO.hashtags);
//			}
//			
//			storyService.create(storyDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	
}
