package app.activityservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.activityservice.service.ReactionService;

@RestController
@RequestMapping(value = "api/reaction")
public class ReactionController {

	public ReactionService reactionService;
	
	@Autowired
	public ReactionController(ReactionService reactionService) {
		this.reactionService = reactionService;
	}
	
}
