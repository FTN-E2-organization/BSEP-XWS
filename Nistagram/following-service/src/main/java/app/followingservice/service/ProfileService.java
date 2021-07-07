package app.followingservice.service;

import java.time.LocalDateTime;
import java.util.Collection;

import app.followingservice.dto.NotificationsSettingsDTO;
import app.followingservice.dto.ProfileDTO;

public interface ProfileService {

	Collection<ProfileDTO> getAllProfiles();
	ProfileDTO getProfileByUsername(String username);
	Collection<ProfileDTO> getFollowingByUsername(String username);
	Collection<ProfileDTO> getFollowersByUsername(String username);
	void createNewFriendship(String startNodeUsername, String endNodeUsername) throws Exception;
	void deleteFriendship(String startNodeUsername, String endNodeUsername);
	void setMuted(String startNodeUsername, String endNodeUsername, boolean isMuted);
	void setClose(String startNodeUsername, String endNodeUsername, boolean isClose);
	void setActivePostNotification(String startNodeUsername, String endNodeUsername, boolean isActivePostNotification);
	void setActiveStoryNotification(String startNodeUsername, String endNodeUsername, boolean isActiveStoryNotification);
	void addProfile(ProfileDTO profileDTO) throws Exception;
	void deleteProfile(String username);
	Collection<ProfileDTO> getProfilesByCategoryName(String categoryName);
	void createFollowRequest(String startNodeUsername, String endNodeUsername) throws Exception;
	void deleteFollowRequest(String startNodeUsername, String endNodeUsername);
	Collection<ProfileDTO> getSendRequests(String username);
	Collection<ProfileDTO> getReceivedRequests(String username);
	LocalDateTime getTimeStampOfRequest(String startNodeUsername, String endNodeUsername);
	void updatePersonalData(String oldUsername, ProfileDTO profileDTO);
	void updateProfilePrivacy(ProfileDTO profileDTO);
	boolean getClose(String startNodeUsername, String endNodeUsername);
	boolean getMuted(String startNodeUsername, String endNodeUsername);
	boolean getActivePostNotification(String startNodeUsername, String endNodeUsername);
	boolean getActiveStoryNotification(String startNodeUsername, String endNodeUsername);
	void createBlocking(String startNodeUsername, String endNodeUsername);
	void deleteBlocking(String startNodeUsername, String endNodeUsername);
	Collection<ProfileDTO> getBlockedProfiles(String username);
	Collection<ProfileDTO> getBlockingProfiles(String username);
	void existsByUsername(String username) throws Exception;
	public Collection<ProfileDTO> getUnmuteFollowingByUsername(String username);
	boolean getActiveLikesNotification(String startNodeUsername, String endNodeUsername);
	boolean getActiveCommentsNotification(String startNodeUsername, String endNodeUsername);
	void blockProfile(String username);
	void setNotifications(NotificationsSettingsDTO dto);
	boolean getActiveMessageNotification(String startNodeUsername, String endNodeUsername);
	NotificationsSettingsDTO getNotificationsSettings(String startNodeUsername, String endNodeUsername);
	boolean isFollow(String startNodeUsername, String endNodeUsername);
	Collection<ProfileDTO> getAllNotBlockingProfiles(String username);
	Collection<ProfileDTO> getRecommendedProfiles(String username);
	
}
