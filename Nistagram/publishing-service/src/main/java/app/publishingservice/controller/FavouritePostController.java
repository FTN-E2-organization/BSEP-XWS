package app.publishingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.publishingservice.dto.AddFavouritePostDTO;
import app.publishingservice.service.CollectionService;
import app.publishingservice.service.FavouritePostService;

@RestController
@RequestMapping(value = "api/favourite-post")
public class FavouritePostController {

	private FavouritePostService favouritePostService;
	private CollectionService collectionService;
	
	@Autowired
	public FavouritePostController(FavouritePostService favouritePostService, CollectionService collectionService) {
		this.favouritePostService = favouritePostService;
		this.collectionService = collectionService;
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody AddFavouritePostDTO favouritePostDTO){
		try {
			/*Username trenutno ulogovanog korisnika ce se preuzeti iz tokena*/
			favouritePostDTO.ownerUsername = "user_1";
			
			if(favouritePostDTO.collectionName != null && !favouritePostDTO.collectionName.isEmpty()) {
				collectionService.createIfDoesNotExist(favouritePostDTO.collectionName);
			}			
					
			favouritePostService.create(favouritePostDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	
	@GetMapping
	public ResponseEntity<?> getAllByUsername(){
		try {
			/*Username trenutno ulogovanog korisnika ce se preuzeti iz tokena*/
			String username = "user_2";
			return new ResponseEntity<>(favouritePostService.getAllByUsername(username), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}		
	
	@GetMapping("/by-collection/{name}")
	public ResponseEntity<?> getAllByUsernameAndCollection(@PathVariable String name){
		try {
			/*Username trenutno ulogovanog korisnika ce se preuzeti iz tokena*/
			String username = "user_2";
			System.out.println(name + "++++");
			return new ResponseEntity<>(favouritePostService.getAllByUsernameAndCollection(username, name), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
}
