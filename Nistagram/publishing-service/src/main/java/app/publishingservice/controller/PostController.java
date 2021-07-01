package app.publishingservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.publishingservice.dto.PostDTO;
import app.publishingservice.exception.BadRequest;
import app.publishingservice.mapper.PostMapper;
import app.publishingservice.model.CustomPrincipal;
import app.publishingservice.service.HashtagService;
import app.publishingservice.service.LocationService;
import app.publishingservice.service.PostService;
import app.publishingservice.service.ProfileService;

@RestController
@RequestMapping(value = "api/publishing/post")
public class PostController {

	private PostService postService;
	private LocationService locationService;
	private HashtagService hashtagService;	
	private ProfileService profileService;
	private static Logger log = LoggerFactory.getLogger(PostController.class);
	
	@Autowired
	public PostController(PostService postService, LocationService locationService, HashtagService hashtagService, ProfileService profileService) {
		this.postService = postService;
		this.locationService = locationService;
		this.hashtagService = hashtagService;
		this.profileService = profileService;
	}	
	
	@PreAuthorize("hasAuthority('createPost')")
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> create(@RequestBody PostDTO postDTO){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
			postDTO.ownerUsername = principal.getUsername();
			
			if(postDTO.location != null && !postDTO.location.isEmpty()) {
				locationService.createIfDoesNotExist(postDTO.location);
			}
			
			if(postDTO.hashtags != null && postDTO.hashtags.size() != 0) {
				hashtagService.createIfDoesNotExist(postDTO.hashtags);
			}			
			
			try {
				log.info(" User create post successful: " + profileService.getIdByUsername(postDTO.ownerUsername));
			} catch (Exception exception) {
			}			
			
			return new ResponseEntity<>(postService.create(postDTO), HttpStatus.CREATED);
		}catch (Exception e) {
			try {				
				log.error(" User create post unsuccessful: " + profileService.getIdByUsername(postDTO.ownerUsername));
			} catch (Exception exception) {
			}			
			return new ResponseEntity<String>("An error occurred while creating post.", HttpStatus.BAD_REQUEST);
		}
	}	
	
	@GetMapping("/username/{username}")
	public ResponseEntity<?> getAllByUsername(@PathVariable String username){
		try {
			return new ResponseEntity<>(PostMapper.toPostDTOs(postService.getAllByUsername(username)), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting posts.", HttpStatus.BAD_REQUEST);
		}
	}	
	
	@GetMapping("/{postId}")
	public ResponseEntity<?> getById(@PathVariable long postId){
		try {
			return new ResponseEntity<>(PostMapper.toPostDTO(postService.getById(postId)), HttpStatus.OK);
		}catch (BadRequest be) {
			return new ResponseEntity<String>(be.getMessage(), HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting post.", HttpStatus.BAD_REQUEST);
		}
	}	
	
	@GetMapping("/location/{locationName}")
	public ResponseEntity<?> getAllByLocationName(@PathVariable String locationName){
		try {
			return new ResponseEntity<>(PostMapper.toPostDTOs(postService.getAllByLocationName(locationName)), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting posts.", HttpStatus.BAD_REQUEST);
		}
	}
		
	@PreAuthorize("hasAuthority('deletePost')")
	@PutMapping("/delete/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable long postId){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
			
			postService.delete(postId);
		
			try {
				log.info(" User delete post successful: " + profileService.getIdByUsername(principal.getUsername()));
			} catch (Exception exception) { 
			}						
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			try {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
				log.error(" User delete post unsuccessful: " + profileService.getIdByUsername(principal.getUsername()));
			} catch (Exception exception) {
			}			
			return new ResponseEntity<String>("An error occurred while deleting post.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/hashtag/{hashtagName}")
	public ResponseEntity<?> getAllByHashtagName(@PathVariable String hashtagName){
		try {
			return new ResponseEntity<>(PostMapper.toPostDTOs(postService.getAllByHashtagName("#" + hashtagName)), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting posts.", HttpStatus.BAD_REQUEST);
		}
	}
	
}
