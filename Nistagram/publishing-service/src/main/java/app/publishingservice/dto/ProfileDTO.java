package app.publishingservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

	public String username;
	public boolean isPublic;
	public boolean allowedTagging;
	public boolean isBlocked;
	
	public ProfileDTO(String username) {
		this.username = username;
	}
	
	public ProfileDTO(String username, boolean isPublic, boolean allowedTagging) {
		this.username = username;
		this.isPublic = isPublic;
		this.allowedTagging = allowedTagging;
	}
	
}
