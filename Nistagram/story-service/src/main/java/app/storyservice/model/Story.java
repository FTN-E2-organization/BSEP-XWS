package app.storyservice.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document
public class Story {

	@Id
	private String idMongo;
	
	@Indexed(unique = true)
	private Long id;
	@JsonProperty
	private boolean isDeleted;

	private String location;

	private String description;

	private boolean forCloseFriends;

	@Indexed(name = "ttlIndex", expireAfter = "1d")
	private LocalDateTime timestamp;

	private List<String> hashtags;
	
	private List<String> tagged;

	@DBRef
	private Profile profile;

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getIdMongo() {
		return idMongo;
	}

	public void setIdMongo(String idMongo) {
		this.idMongo = idMongo;
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

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public List<String> getTagged() {
		return tagged;
	}

	public void setTagged(List<String> tagged) {
		this.tagged = tagged;
	}

}
