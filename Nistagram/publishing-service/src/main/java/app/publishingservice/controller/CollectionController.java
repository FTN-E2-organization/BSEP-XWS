package app.publishingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.publishingservice.model.CustomPrincipal;
import app.publishingservice.service.CollectionService;

@RestController
@RequestMapping(value = "api/publishing/collection")
public class CollectionController {
	
	private CollectionService collectionService;
	
	@Autowired
	public CollectionController(CollectionService collectionService) {
		this.collectionService = collectionService;
	}
	
	@PreAuthorize("hasAuthority('getCollectionsByUsername')")
	@GetMapping
	public ResponseEntity<?> getAllByUsername(){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
			String username = principal.getUsername();
			return new ResponseEntity<>(collectionService.getAllByUsername(username), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	
}
