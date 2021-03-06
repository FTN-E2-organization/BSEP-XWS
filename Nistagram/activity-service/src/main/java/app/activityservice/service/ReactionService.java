package app.activityservice.service;

import java.util.Collection;

import app.activityservice.dto.AddReactionDTO;
import app.activityservice.dto.NumberOfReactionsDTO;
import app.activityservice.model.Reaction;

public interface ReactionService {

	Boolean create(AddReactionDTO reactionDTO);

	Collection<Reaction> getLikesByPostId(long postId);
	
	Collection<Reaction> getDislikesByPostId(long postId);

	Collection<Reaction> getLikesByUsername(String username);
	
	Collection<Reaction> getDislikesByUsername(String username);

	Collection<Reaction> getLikesByAdId(long adId);

	Collection<Reaction> getDislikesByAdId(long adId);

	NumberOfReactionsDTO getNumberOfReactionsByAdId(long id);
	
}
