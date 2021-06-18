package app.notificationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProfileDTO {

	private String username;	
	
	@JsonProperty
	private boolean isBlocked;
	
	public ProfileDTO(String username) {
		this.username = username;
	}

	public ProfileDTO(String username, boolean isBlocked) {
		this.username = username;
		this.isBlocked = isBlocked;
	}
	
}
