package app.publishingservice.dto;

import java.util.List;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class AddStoryDTO {

	public String ownerUsername;
	public String description;
	public boolean isHighlight;
	public boolean forCloseFriends;
	public String location;
	public List<String> hashtags;
	public List<String> taggedUsernames;
	
}
