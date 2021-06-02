package app.activityservice.mapper;

import java.util.ArrayList;
import java.util.Collection;

import app.activityservice.dto.ReactionDTO;
import app.activityservice.model.Reaction;

public class ReactionMapper {

	public static Collection<ReactionDTO> toReactionDTOs(Collection<Reaction> reactions) {
		Collection<ReactionDTO> reactionDTOs = new ArrayList<>();
		for (Reaction c : reactions) {
			ReactionDTO dto = new ReactionDTO();
			dto.postId = c.getPostId();
			dto.postType = c.getPostType().toString();
			dto.reactionType = c.getReactionType().toString();
			dto.ownerUsername = c.getOwner().getUsername();
			reactionDTOs.add(dto);
		}		
		return reactionDTOs;
	}	
	
}
