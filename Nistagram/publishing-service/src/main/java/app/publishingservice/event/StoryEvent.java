package app.publishingservice.event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import app.publishingservice.dto.StoryDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class StoryEvent {

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
	private StoryEventType type;

	public StoryEvent(String transactionId, StoryDTO storyDTO, StoryEventType type) {
		this.transactionId = transactionId;
		this.storyId = storyDTO.id;
		this.timestamp = storyDTO.timestamp;
		this.description = storyDTO.description;
		this.isHighlight = storyDTO.isHighlight;
		this.isDeleted = storyDTO.isDeleted;
		this.forCloseFriends = storyDTO.forCloseFriends;
		this.ownerUsername = storyDTO.ownerUsername;
		this.location = checkStringInputValueAndSet(storyDTO.location);
		this.hashtags = checkStringListInputValuesAndSet(storyDTO.hashtags);
		this.tagged = checkStringListInputValuesAndSet(storyDTO.taggedUsernames);
		this.type = type;
	}
	
	public StoryEvent(String transactionId, Long storyId, StoryEventType type) {
		this.transactionId = transactionId;
		this.storyId = storyId;
		this.type = type;
	}
	
	private String checkStringInputValueAndSet(String input) {
		if(input != null) return input;
		else return "";
	}
	
	private ArrayList<String> checkStringListInputValuesAndSet(List<String> input){
		if(input != null && input.size() > 0) return (ArrayList<String>) input;
		else return new ArrayList<String>();
	}
	
}
