package app.publishingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.publishingservice.service.HashtagService;

@RestController
@RequestMapping(value = "api/publishing/hashtag")
public class HashtagController {

	private HashtagService hashtagService;
	
	@Autowired 
	public HashtagController(HashtagService hashtagService) {
		this.hashtagService = hashtagService;
	}
	
	@GetMapping
	public ResponseEntity<?> getAll() {
		try {
			return new ResponseEntity<>(hashtagService.getAll(), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting hashtags.", HttpStatus.BAD_REQUEST);
		}
	}
}
