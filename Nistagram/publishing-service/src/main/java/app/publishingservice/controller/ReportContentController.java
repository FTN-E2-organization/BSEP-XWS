package app.publishingservice.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import app.publishingservice.dto.ReportContentRequestDTO;
import app.publishingservice.enums.ContentType;
import app.publishingservice.model.CustomPrincipal;
import app.publishingservice.service.PostService;
import app.publishingservice.service.ReportContentService;
import app.publishingservice.service.StoryService;

@RestController
@RequestMapping(value = "api/publishing/report-content")
public class ReportContentController {

	private ReportContentService reportContentService;
	private PostService postService;
	private StoryService storyService;
	
	@Autowired
	public ReportContentController(ReportContentService reportContentService,PostService postService,StoryService storyService) {
		this.reportContentService = reportContentService;
		this.postService = postService;
		this.storyService = storyService;
	}
	
	@PreAuthorize("hasAuthority('reportContent')")
	@PostMapping
	public ResponseEntity<?> reportContent(@RequestBody ReportContentRequestDTO contentRequestDTO) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
	        contentRequestDTO.initiatorUsername = principal.getUsername();
	        
			reportContentService.create(contentRequestDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while creating report content request.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('getContentRequests')")
	@GetMapping
	public ResponseEntity<?> getAll(){
		try {
			return new ResponseEntity<Collection<ReportContentRequestDTO>>(reportContentService.getAll(), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting report content request.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('removeContent')")
	@PutMapping("/remove/{contentId}/{type}")
	public ResponseEntity<?> removeContent(@PathVariable Long contentId, @PathVariable ContentType type){
		try {
			if(type == ContentType.post) 
				postService.delete(contentId);
			else
				storyService.delete(contentId);
			
			reportContentService.removeContent(contentId, type);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while removing report content request.", HttpStatus.BAD_REQUEST);
		}
	}
}
