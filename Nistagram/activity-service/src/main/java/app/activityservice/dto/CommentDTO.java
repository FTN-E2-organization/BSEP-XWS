package app.activityservice.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

	public LocalDateTime timestamp;
	public String text;
	public long postId;
	public String postType;
	public String ownerUsername;
	public List<String> taggedUsernames = new ArrayList<>();
	
}
