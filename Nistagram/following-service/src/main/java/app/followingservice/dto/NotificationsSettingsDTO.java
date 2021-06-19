package app.followingservice.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class NotificationsSettingsDTO {

	public boolean activeLikesNotification;
	public boolean activeCommentNotification;
	public boolean activeStoryNotification;
	public boolean activePostNotification;
	public boolean activeMessageNotification;
	public String followingUsername;
	public String loggedInUsername;
	
}
