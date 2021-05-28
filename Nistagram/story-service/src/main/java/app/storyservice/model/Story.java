package app.storyservice.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Story {

	@Id
	private String idMongo;
	
	private Long id;

	private boolean isDeleted;

	private String username; // IZBRISIIII

	private String email;

	private LocalDateTime timestamp;

	private String location;

	private String description;
	
	private boolean forCloseFriends;
	
	private LocalDateTime ttl;
	
	private List<String> tags;
	
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

}
