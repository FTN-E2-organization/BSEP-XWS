package app.activityservice.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ReactionDTO {

	public long postId;
	public String postType;
	public String reactionType;
	public String ownerUsername;
	
}
