package app.activityservice.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AddCommentDTO {

	public String text;
	public long postId;
	public String postType;
	public String ownerUsername;
	public List<String> taggedUsernames = new ArrayList<>();
}
