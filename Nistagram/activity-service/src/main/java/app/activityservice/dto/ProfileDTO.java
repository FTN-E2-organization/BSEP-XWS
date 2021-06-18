package app.activityservice.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

	public String username;
	public boolean allowedTagging;
	public boolean isBlocked;
	
	
	public ProfileDTO(String username) {
		this.username = username;
	}
	
	public ProfileDTO(String username, boolean allowedTagging) {
		this.username = username;
		this.allowedTagging = allowedTagging;
	}
	
}
