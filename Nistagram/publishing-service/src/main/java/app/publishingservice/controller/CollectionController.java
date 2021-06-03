package app.publishingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.publishingservice.service.CollectionService;

@RestController
@RequestMapping(value = "api/publishing/collection")
public class CollectionController {
	
	private CollectionService collectionService;
	
	@Autowired
	public CollectionController(CollectionService collectionService) {
		this.collectionService = collectionService;
	}
	
	@GetMapping
	public ResponseEntity<?> getAllByUsername(){
		try {
			/*Username trenutno ulogovanog korisnika ce se preuzeti iz tokena*/
			String username = "pero123";
			return new ResponseEntity<>(collectionService.getAllByUsername(username), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	
}
