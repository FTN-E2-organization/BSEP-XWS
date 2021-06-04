package app.publishingservice.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class FavouritePostDTO {

	public long id;
	public long postId;
	public LocalDateTime postTimeStamp;
	public String description;
	public String collectionName;
	public String postOwnerUsername;
	public List<String> postTaggedUsernames;
	
}
