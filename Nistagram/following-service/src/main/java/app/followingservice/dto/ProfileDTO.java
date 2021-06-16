package app.followingservice.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

	public String username;
	public boolean isPublic;
	
	public ProfileDTO(String username) {
		this.username = username;
	}

}
