package app.campaignservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
	public String username;
	public boolean isInfluencer;
	public boolean isPublic;
	public boolean isBlocked;
}
