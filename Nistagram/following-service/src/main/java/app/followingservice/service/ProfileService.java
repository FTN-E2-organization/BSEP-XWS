package app.followingservice.service;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.data.neo4j.repository.query.Query;

import app.followingservice.dto.ProfileDTO;

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
	void addProfile(ProfileDTO profileDTO);
	void deleteProfile(String username);
	Collection<ProfileDTO> getProfilesByCategoryName(String categoryName);
	void createFollowRequest(String startNodeUsername, String endNodeUsername);
	void deleteFollowRequest(String startNodeUsername, String endNodeUsername);
	Collection<ProfileDTO> getSendRequests(String username);
	Collection<ProfileDTO> getReceivedRequests(String username);
	LocalDateTime getTimeStampOfRequest(String startNodeUsername, String endNodeUsername);
	void updatePersonalData(String oldUsername, ProfileDTO profileDTO);
	boolean getClose(String startNodeUsername, String endNodeUsername);
	boolean getMuted(String startNodeUsername, String endNodeUsername);
	boolean getActivePostNotification(String startNodeUsername, String endNodeUsername);
	boolean getActiveStoryNotification(String startNodeUsername, String endNodeUsername);
	
}
