package app.followingservice.service;

import java.time.LocalDateTime;
import java.util.Collection;
import app.followingservice.dto.ProfileDTO;
import app.followingservice.model.Profile;

public interface ProfileService {

	Collection<ProfileDTO> getAllProfiles();
	ProfileDTO getProfileByUsername(String username);
	Collection<ProfileDTO> getFollowingByUsername(String username);
	Collection<ProfileDTO> getFollowersByUsername(String username);
	void createNewFriendship(String startNodeUsername, String endNodeUsername);
	void deleteFriendship(String startNodeUsername, String endNodeUsername);
	void setMuted(String startNodeUsername, String endNodeUsername, boolean isMuted);
	void setClose(String startNodeUsername, String endNodeUsername, boolean isClose);
	void setActivePostNotification(String startNodeUsername, String endNodeUsername, boolean isActivePostNotification);
	void setActiveStoryNotification(String startNodeUsername, String endNodeUsername, boolean isActiveStoryNotification);
	void addProfile(ProfileDTO profileDTO) throws Exception;
	void deleteProfile(String username);
	Collection<ProfileDTO> getProfilesByCategoryName(String categoryName);
	void createFollowRequest(String startNodeUsername, String endNodeUsername);
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
	boolean existsByUsername(String username);
	public Collection<ProfileDTO> getUnmuteFollowingByUsername(String username);
	void setIsBlocked(String username, boolean isBlocked);
}
