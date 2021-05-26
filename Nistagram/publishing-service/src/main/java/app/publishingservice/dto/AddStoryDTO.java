package app.publishingservice.dto;

import java.util.List;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class AddStoryDTO {

	public Long ownerId;
	public String description;
	public boolean isHighlight;
	public boolean forCloseFriends;
	public String location;
	public List<String> hashtags;
	public List<Long> tagged;
	
}
