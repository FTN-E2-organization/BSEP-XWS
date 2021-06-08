package app.storyservice.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoryDTO {

	private Long id;

	private boolean isDeleted;

	private LocalDateTime timestamp;

	private String location;

	private String description;

	private boolean forCloseFriends;

	private List<String> hashtags = new ArrayList<>();
	
	private List<String> taggedUsernames = new ArrayList<>();

	private String ownerUsername;

}
