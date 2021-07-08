package app.activityservice.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.activityservice.dto.AddReactionDTO;
import app.activityservice.enums.PostType;
import app.activityservice.enums.ReactionType;
import app.activityservice.model.Reaction;
import app.activityservice.repository.ProfileRepository;
import app.activityservice.repository.ReactionRepository;

@Service
public class ReactionServiceImpl implements ReactionService {

	private ReactionRepository reactionRepository;
	private ProfileRepository profileRepository;
	
	@Autowired
	public ReactionServiceImpl(ReactionRepository reactionRepository, ProfileRepository profileRepository) {	
		this.reactionRepository = reactionRepository;
		this.profileRepository = profileRepository;
	}

	@Override
	public Boolean create(AddReactionDTO reactionDTO) {
		int postType = 0;
		if (!reactionDTO.postType.equals("regular"))
			postType = 1;
		Reaction reaction = reactionRepository.findByUsernameAndPostIDAndPostType(reactionDTO.ownerUsername, reactionDTO.postId, postType);
		
		if (reaction != null) {
			if (reaction.getReactionType() == ReactionType.like && reactionDTO.reactionType.equals("like")) {
				reaction.setReactionType(ReactionType.no_reaction);
				reactionRepository.save(reaction);
				return false;
			}
			else if (reaction.getReactionType() == ReactionType.dislike && reactionDTO.reactionType.equals("dislike")) {
				reaction.setReactionType(ReactionType.no_reaction);
				reactionRepository.save(reaction);
				return false;
			}
			else {
				reaction.setReactionType(ReactionType.valueOf(reactionDTO.reactionType));
				reactionRepository.save(reaction);
				if (reactionDTO.reactionType.equals("like")) {
					return true;
				}
				return false;
			}			
		}
				
		reaction = new Reaction();
		reaction.setReactionType(ReactionType.valueOf(reactionDTO.reactionType));
		reaction.setPostId(reactionDTO.postId);
		reaction.setPostType(PostType.valueOf(reactionDTO.postType));
		reaction.setOwner(profileRepository.findByUsername(reactionDTO.ownerUsername));		
		reactionRepository.save(reaction);
		if (reactionDTO.reactionType.equals("like")) {
			return true;
		}
		return false;
	}

	@Override
	public Collection<Reaction> getLikesByPostId(long postId) {
		return reactionRepository.findLikesByPostIdAndPostType(postId, 0);
	}
	
	@Override
	public Collection<Reaction> getLikesByAdId(long adId) {
		return reactionRepository.findLikesByPostIdAndPostType(adId, 1);
	}

	@Override
	public Collection<Reaction> getDislikesByPostId(long postId) {
		return reactionRepository.findDislikesByPostIdAndPostType(postId, 0);
	}
	
	@Override
	public Collection<Reaction> getDislikesByAdId(long adId) {
		return reactionRepository.findDislikesByPostIdAndPostType(adId, 1);
	}

	@Override
	public Collection<Reaction> getLikesByUsername(String username) {
		return reactionRepository.findLikesByUsername(username);
	}

	@Override
	public Collection<Reaction> getDislikesByUsername(String username) {
		return reactionRepository.findDislikesByUsername(username);
	}


}
