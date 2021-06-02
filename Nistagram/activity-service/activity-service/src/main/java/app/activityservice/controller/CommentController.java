package app.activityservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.activityservice.dto.AddCommentDTO;
import app.activityservice.service.CommentService;

@RestController
@RequestMapping(value = "api/comment")
public class CommentController {

	private CommentService commentService;
	
	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody AddCommentDTO commentDTO){
		try {
			/*Username trenutno ulogovanog korisnika ce se preuzeti iz tokena*/
			
			commentService.create(commentDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}		
	
	
}
