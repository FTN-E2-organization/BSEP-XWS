package app.storyservice.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import app.storyservice.model.Profile;

public class StoryDTO {

	private Long id;

	private boolean isDeleted;

	private LocalDateTime timestamp;

	private String location;

	private String description;

	private boolean forCloseFriends;

	private List<String> hashtags;
	
	private List<String> tagged;

	private String ownerUsername;

	public StoryDTO() {
	}

	public Long getId() {
		return id;
	}

	public StoryDTO(Long id, boolean isDeleted, LocalDateTime timestamp, String location, String description,
			boolean forCloseFriends, List<String> hashtags,List<String> tagged, String ownerUsername) {
		super();
		this.id = id;
		this.isDeleted = isDeleted;
		this.timestamp = timestamp;
		this.location = location;
		this.description = description;
		this.forCloseFriends = forCloseFriends;
		this.hashtags = hashtags;
		this.tagged = tagged;
		this.ownerUsername = ownerUsername;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isForCloseFriends() {
		return forCloseFriends;
	}

	public void setForCloseFriends(boolean forCloseFriends) {
		this.forCloseFriends = forCloseFriends;
	}

	public List<String> getHashtags() {
		return hashtags;
	}

	public void setHashtags(List<String> tags) {
		this.hashtags = tags;
	}

	public String getOwnerUsername() {
		return ownerUsername;
	}

	public void setOwnerUsername(String ownerUsername) {
		this.ownerUsername = ownerUsername;
	}

	public List<String> getTagged() {
		return tagged;
	}

	public void setTagged(List<String> tagged) {
		this.tagged = tagged;
	}

}
