package app.followingservice.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

	public String username;
	public boolean isPublic;
	public boolean isBlocked;
	
	public ProfileDTO(String username) {
		this.username = username;
	}
	
	public ProfileDTO(String username, boolean isPublic) {
		this.username = username;
		this.isPublic = isPublic;
	}

}
