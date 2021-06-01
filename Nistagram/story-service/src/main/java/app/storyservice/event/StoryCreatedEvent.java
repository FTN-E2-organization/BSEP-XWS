package app.storyservice.event;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StoryCreatedEvent {

	private String transactionId;
	private Long storyId;
	private LocalDateTime timestamp;
	private String description;
	private boolean isHighlight;
	private boolean isDeleted;
	private boolean forCloseFriends;
	private String ownerUsername;
	private String location;
	private List<String> hashtags;
	private List<String> tagged;
	private List<File> files;
	
}
