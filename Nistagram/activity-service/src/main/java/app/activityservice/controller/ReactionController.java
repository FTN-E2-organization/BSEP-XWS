package app.activityservice.controller;

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

import app.activityservice.dto.AddReactionDTO;
import app.activityservice.mapper.ReactionMapper;
import app.activityservice.model.CustomPrincipal;
import app.activityservice.service.ReactionService;

@RestController
@RequestMapping(value = "api/activity/reaction")
public class ReactionController {

	public ReactionService reactionService;
	
	@Autowired
	public ReactionController(ReactionService reactionService) {
		this.reactionService = reactionService;
	}
	
	@PreAuthorize("hasAuthority('createReaction')")
	@PostMapping
	public ResponseEntity<?> create(@RequestBody AddReactionDTO reactionDTO){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
	        reactionDTO.ownerUsername = principal.getUsername();
			
			reactionService.create(reactionDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while creating reaction. " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@GetMapping("/likes/{postId}")
	public ResponseEntity<?> getLikesByPostId(@PathVariable long postId){
		try {
			return new ResponseEntity<>(ReactionMapper.toReactionDTOs(reactionService.getLikesByPostId(postId)), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting likes.", HttpStatus.BAD_REQUEST);
		}
	}	
		
	@GetMapping("/dislikes/{postId}")
	public ResponseEntity<?> getDislikesByPostId(@PathVariable long postId){
		try {
			return new ResponseEntity<>(ReactionMapper.toReactionDTOs(reactionService.getDislikesByPostId(postId)), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting dislikes.", HttpStatus.BAD_REQUEST);
		}
	}
	
//	@PreAuthorize("hasAuthority('getReactions')")
	@GetMapping("/likes/by-username/{username}")
	public ResponseEntity<?> getLikedPostsByUsername(@PathVariable String username){
		try {
//			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
//	        String username = principal.getUsername();
			return new ResponseEntity<>(ReactionMapper.toReactionDTOs(reactionService.getLikesByUsername(username)), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage() + " - An error occurred while getting likes.", HttpStatus.BAD_REQUEST);
		}
	}		

//	@PreAuthorize("hasAuthority('getReactions')")
	@GetMapping("/dislikes/by-username/{username}")
	public ResponseEntity<?> getDislikedPostsByUsername(@PathVariable String username){
		try {
//			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
//	        String username = principal.getUsername();
			return new ResponseEntity<>(ReactionMapper.toReactionDTOs(reactionService.getDislikesByUsername(username)), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage() + " - An error occurred while getting posts.", HttpStatus.BAD_REQUEST);
		}
	}
	
}
