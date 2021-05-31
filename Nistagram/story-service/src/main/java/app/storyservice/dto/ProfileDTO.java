package app.storyservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProfileDTO {
	
	private String username;
	
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

	@JsonProperty
	private boolean isPublic;

	@JsonProperty
	private boolean isDeleted;

	public ProfileDTO() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
