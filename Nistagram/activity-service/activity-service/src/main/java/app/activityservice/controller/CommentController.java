package app.activityservice.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.activityservice.dto.AddCommentDTO;
import app.activityservice.dto.CommentDTO;
import app.activityservice.mapper.CommentMapper;
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
	
	@GetMapping("/{postId}/post-id")
	public ResponseEntity<?> findAllByPostId(@PathVariable long postId){
		try {
			Collection<CommentDTO> commentDTOs = CommentMapper.toCommentDTOs(commentService.findAllByPostId(postId));
			return new ResponseEntity<Collection<CommentDTO>>(commentDTOs, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}	
	
}
