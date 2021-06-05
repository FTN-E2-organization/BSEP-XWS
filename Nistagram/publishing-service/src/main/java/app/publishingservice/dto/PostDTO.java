package app.publishingservice.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

	public Long id;
	public String ownerUsername;
	public String description;
	public String location;
	public boolean isDeleted;
	public LocalDateTime timestamp;
	public List<String> hashtags = new ArrayList<>();
	public List<String> taggedUsernames = new ArrayList<>();

}
