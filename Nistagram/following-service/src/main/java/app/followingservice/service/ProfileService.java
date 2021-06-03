package app.followingservice.service;

import java.time.LocalDateTime;
import java.util.Collection;

import app.followingservice.dto.ProfileDTO;
import app.followingservice.model.Profile;

public interface ProfileService {

	Collection<Profile> getAllProfiles();
	Profile getProfileByUsername(String username);
	Collection<Profile> getFollowingByUsername(String username);
	Collection<Profile> getFollowersByUsername(String username);
	void createNewFriendship(String startNodeUsername, String endNodeUsername);
	void deleteFriendship(String startNodeUsername, String endNodeUsername);
	void setMuted(String startNodeUsername, String endNodeUsername, boolean isMuted);
	void setClose(String startNodeUsername, String endNodeUsername, boolean isClose);
	void setActivePostNotification(String startNodeUsername, String endNodeUsername, boolean isActivePostNotification);
	void setActiveStoryNotification(String startNodeUsername, String endNodeUsername, boolean isActiveStoryNotification);
	void addProfile(ProfileDTO profileDTO);
	void deleteProfile(String username);
	Collection<Profile> getProfilesByCategoryName(String categoryName);
	void createFollowRequest(String startNodeUsername, String endNodeUsername);
	void deleteFollowRequest(String startNodeUsername, String endNodeUsername);
	Collection<Profile> getSendRequests(String username);
	Collection<Profile> getReceivedRequests(String username);
	LocalDateTime getTimeStampOfRequest(String startNodeUsername, String endNodeUsername);
	void updatePersonalData(String oldUsername, ProfileDTO profileDTO);
	
}
