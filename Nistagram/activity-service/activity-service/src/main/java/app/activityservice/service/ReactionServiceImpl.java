package app.activityservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.activityservice.repository.ReactionRepository;

@Service
public class ReactionServiceImpl implements ReactionService {

	private ReactionRepository reactionRepository;
	
	@Autowired
	public ReactionServiceImpl(ReactionRepository reactionRepository) {	
		this.reactionRepository = reactionRepository;
	}
}
