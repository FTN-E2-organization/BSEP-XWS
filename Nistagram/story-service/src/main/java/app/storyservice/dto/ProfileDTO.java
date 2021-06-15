package app.storyservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProfileDTO {
	
	private String username;
	
	@JsonProperty
	private boolean isPublic;

	@JsonProperty
	private boolean isDeleted;	
	
	
	public ProfileDTO(String username) {
		this.username = username;
	}
	
	public ProfileDTO(String username, boolean isPublic) {
		this.username = username;
		this.isPublic = isPublic;
	}

}
