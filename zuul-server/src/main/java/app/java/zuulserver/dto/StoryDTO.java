package app.java.zuulserver.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
	public List<String> hashtags;
	public List<String> taggedUsernames;
}
