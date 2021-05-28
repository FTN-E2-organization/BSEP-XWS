package app.storyservice.dto;

import java.time.LocalDateTime;
import java.util.List;

import app.storyservice.model.Profile;

public class AddStoryDTO {

	private Long id;

	private boolean isDeleted;

	private LocalDateTime timestamp;

	private String location;

	private String description;

	private boolean forCloseFriends;

	private LocalDateTime ttl;

	private List<String> tags;

	private ProfileDTO profileDto;

	public AddStoryDTO() {
	}

	public Long getId() {
		return id;
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

	public LocalDateTime getTtl() {
		return ttl;
	}

	public void setTtl(LocalDateTime ttl) {
		this.ttl = ttl;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public ProfileDTO getProfileDto() {
		return profileDto;
	}

	public void setProfileDto(ProfileDTO profileDto) {
		this.profileDto = profileDto;
	}

}
