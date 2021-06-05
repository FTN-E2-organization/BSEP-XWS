package app.publishingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.publishingservice.dto.PostDTO;
import app.publishingservice.mapper.PostMapper;
import app.publishingservice.service.HashtagService;
import app.publishingservice.service.LocationService;
import app.publishingservice.service.PostService;

@RestController
@RequestMapping(value = "api/publishing/post")
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
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> create(@RequestBody PostDTO postDTO){
		try {
			/*Username trenutno ulogovanog korisnika ce se preuzeti iz tokena*/
			postDTO.ownerUsername = "pero123";
			
			if(postDTO.location != null && !postDTO.location.isEmpty()) {
				locationService.createIfDoesNotExist(postDTO.location);
			}
			
			if(postDTO.hashtags != null && postDTO.hashtags.size() != 0) {
				hashtagService.createIfDoesNotExist(postDTO.hashtags);
			}
			
			return new ResponseEntity<>(postService.create(postDTO), HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@GetMapping("/username/{username}")
	public ResponseEntity<?> getAllByUsername(@PathVariable String username){
		try {
			return new ResponseEntity<>(PostMapper.toPostDTOs(postService.getAllByUsername(username)), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@GetMapping("/{postId}")
	public ResponseEntity<?> getById(@PathVariable long postId){
		try {
			return new ResponseEntity<>(PostMapper.toPostDTO(postService.getById(postId)), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@PutMapping("/delete/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable long postId){
		try {
			postService.delete(postId);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
}
