package app.publishingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.publishingservice.dto.AddFavouritePostDTO;
import app.publishingservice.service.FavouritePostService;

@RestController
@RequestMapping(value = "api/favourite-post")
public class FavouritePostController {

	private FavouritePostService favouritePostService;
	
	@Autowired
	public FavouritePostController(FavouritePostService favouritePostService) {
		this.favouritePostService = favouritePostService;
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody AddFavouritePostDTO favouritePostDTO){
		try {
			/*Username trenutno ulogovanog korisnika ce se preuzeti iz tokena*/
					
			favouritePostService.create(favouritePostDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	
}
