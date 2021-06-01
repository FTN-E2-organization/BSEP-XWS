package app.publishingservice.dto;

import java.io.File;
import java.time.LocalDateTime;
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
	public List<String> hashtags;
	public List<String> taggedUsernames;
	public List<File> files;
}
