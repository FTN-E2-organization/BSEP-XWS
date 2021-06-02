package app.activityservice.service;

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
	public void create(AddReactionDTO reactionDTO) {
//		Reaction reaction = reactionRepository.findByUsernameAndPostID(reactionDTO.ownerUsername, reactionDTO.postId);
		
//		if (reaction != null) {
//			if (reaction.getReactionType() == ReactionType.like && reactionDTO.reactionType.equals("like")) {
//				reactionRepository.delete(reaction);
//			}
//			else if (reaction.getReactionType() == ReactionType.like && reactionDTO.reactionType.equals("dislike")) {
//				reaction.setReactionType(ReactionType.dislike);
//				reactionRepository.save(reaction);
//			}
//			else if (reaction.getReactionType() == ReactionType.dislike && reactionDTO.reactionType.equals("dislike")) {
//				reactionRepository.delete(reaction);
//			}
//			else if (reaction.getReactionType() == ReactionType.dislike && reactionDTO.reactionType.equals("like")) {
//				reaction.setReactionType(ReactionType.like);
//				reactionRepository.save(reaction);
//			}			
//			return;
//		}
				
		Reaction reaction = new Reaction();
		reaction.setReactionType(ReactionType.valueOf(reactionDTO.reactionType));
		reaction.setPostId(reactionDTO.postId);
		reaction.setPostType(PostType.valueOf(reactionDTO.postType));
		reaction.setOwner(profileRepository.findByUsername(reactionDTO.ownerUsername));
		
		reactionRepository.save(reaction);
	}
}
