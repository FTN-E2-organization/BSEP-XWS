package app.activityservice.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AddReactionDTO {

	public String reactionType;
	public long postId;
	public String postType;
	public String ownerUsername;
	
}
