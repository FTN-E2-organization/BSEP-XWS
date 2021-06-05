package app.publishingservice.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class StoryDTO {

	public Long id;
	public String ownerUsername;
	public String description;
	public boolean isHighlight;
	public boolean forCloseFriends;
	public String location;
	public boolean isDeleted;
	public LocalDateTime timestamp;
	public List<String> hashtags = new ArrayList<>();
	public List<String> taggedUsernames = new ArrayList<>();
}
