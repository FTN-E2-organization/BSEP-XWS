package app.publishingservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AddPostDTO {

	public String ownerUsername;
	public String description;
	public String location;
	public List<String> hashtags;
	public List<String> taggedUsernames;	
	
}
