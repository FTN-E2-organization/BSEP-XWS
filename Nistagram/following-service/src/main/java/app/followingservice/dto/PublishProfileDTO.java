package app.followingservice.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PublishProfileDTO {

	public String username;
	public boolean isPublic;
	public boolean isVerified;
	public boolean allowedUnfollowerMessages;
	public boolean allowedTagging;
	public boolean isBlocked;
	public String category;
}
