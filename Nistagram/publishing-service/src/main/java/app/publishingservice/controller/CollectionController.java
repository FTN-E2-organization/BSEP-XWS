package app.publishingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.publishingservice.service.CollectionService;

@RestController
@RequestMapping(value = "api/collection")
public class CollectionController {
	
	private CollectionService collectionService;
	
	@Autowired
	public CollectionController(CollectionService collectionService) {
		this.collectionService = collectionService;
	}
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		try {
			return new ResponseEntity<>(collectionService.getAll(), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	
}
