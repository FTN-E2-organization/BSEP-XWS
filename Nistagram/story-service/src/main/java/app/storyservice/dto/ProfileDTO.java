package app.storyservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProfileDTO {
	
	private String username;
	
	@JsonProperty
	private boolean isPublic;

	@JsonProperty
	private boolean isDeleted;	
	
	public ProfileDTO(String username, boolean isPublic, boolean isDeleted) {
		super();
		this.username = username;
		this.isPublic = isPublic;
		this.isDeleted = isDeleted;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public ProfileDTO() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
