package app.java.zuulserver.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PostAndReactionsDTO {

	public Long postId;
	public String ownerUsername;
	public String description;
	public String location;
	public boolean isDeleted;
	public LocalDateTime timestamp;
	public List<String> hashtags = new ArrayList<>();
	public List<String> taggedUsernames = new ArrayList<>();	
	
	
}
