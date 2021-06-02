package app.activityservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.activityservice.dto.AddReactionDTO;
import app.activityservice.service.ReactionService;

@RestController
@RequestMapping(value = "api/reaction")
public class ReactionController {

	public ReactionService reactionService;
	
	@Autowired
	public ReactionController(ReactionService reactionService) {
		this.reactionService = reactionService;
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody AddReactionDTO reactionDTO){
		try {
			/*Username trenutno ulogovanog korisnika ce se preuzeti iz tokena*/
			
			reactionService.create(reactionDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	
}
