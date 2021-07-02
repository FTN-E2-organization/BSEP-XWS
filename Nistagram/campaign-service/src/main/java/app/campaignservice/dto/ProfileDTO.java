package app.campaignservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
	
	public String username;
	public boolean isInfluencer;
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
