package app.publishingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.publishingservice.model.CustomPrincipal;
import app.publishingservice.dto.AddFavouritePostDTO;
import app.publishingservice.mapper.FavouritePostMapper;
import app.publishingservice.service.CollectionService;
import app.publishingservice.service.FavouritePostService;

@RestController
@RequestMapping(value = "api/publishing/favourite-post")
public class FavouritePostController {

	private FavouritePostService favouritePostService;
	private CollectionService collectionService;
	
	@Autowired
	public FavouritePostController(FavouritePostService favouritePostService, CollectionService collectionService) {
		this.favouritePostService = favouritePostService;
		this.collectionService = collectionService;
	}
	
	@PreAuthorize("hasAuthority('createFavouritePost')")
	@PostMapping
	public ResponseEntity<?> create(@RequestBody AddFavouritePostDTO favouritePostDTO){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
	        favouritePostDTO.ownerUsername = principal.getUsername();
			
			if(favouritePostDTO.collectionName != null && !favouritePostDTO.collectionName.isEmpty()) {
				collectionService.createIfDoesNotExist(favouritePostDTO.collectionName);
			}			
					
			favouritePostService.create(favouritePostDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@PreAuthorize("hasAuthority('getFavouritePosts')")
	@GetMapping
	public ResponseEntity<?> getAllByUsername(){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
			String username = principal.getUsername();
			
			return new ResponseEntity<>(FavouritePostMapper.toFavouritePostDTOs(favouritePostService.getAllByUsername(username)), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}		
	
	@PreAuthorize("hasAuthority('getFavouritePosts')")
	@GetMapping("/by-collection/{name}")
	public ResponseEntity<?> getAllByUsernameAndCollection(@PathVariable String name){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
			String username = principal.getUsername();
			
			return new ResponseEntity<>(FavouritePostMapper.toFavouritePostDTOs(favouritePostService.getAllByUsernameAndCollection(username, name)), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
}
