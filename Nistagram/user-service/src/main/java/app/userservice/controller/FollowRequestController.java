package app.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import app.userservice.dto.*;
import app.userservice.model.*;
import app.userservice.service.*;


@RestController
@RequestMapping(value = "api/follow-request")
public class FollowRequestController {

	private FollowRequestService followRequestService;
	
	@Autowired
	public FollowRequestController(FollowRequestService followRequestService) {
		this.followRequestService = followRequestService;
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody FollowRequestDTO followRequestDTO) {
		try {
			followRequestService.create(followRequestDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
