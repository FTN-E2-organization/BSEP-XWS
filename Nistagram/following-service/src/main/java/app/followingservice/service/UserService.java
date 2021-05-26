package app.followingservice.service;

import java.time.LocalDateTime;
import java.util.Collection;

import app.followingservice.dto.UserDTO;
import app.followingservice.model.User;

public interface UserService {

	Collection<User> getAllUsers();
	Collection<User> getFollowingByUsername(String username);
	Collection<User> getFollowersByUsername(String username);
	void createNewFriendship(String startNodeUsername, String endNodeUsername);
	void deleteFriendship(String startNodeUsername, String endNodeUsername);
	void setMuted(String startNodeUsername, String endNodeUsername, boolean isMuted);
	void setClose(String startNodeUsername, String endNodeUsername, boolean isClose);
	void setActivePostNotification(String startNodeUsername, String endNodeUsername, boolean isActivePostNotification);
	void setActiveStoryNotification(String startNodeUsername, String endNodeUsername, boolean isActiveStoryNotification);
	void addNewUser(UserDTO userDTO);
	void deleteUser(String username);
	Collection<User> getUsersByCategoryName(String categoryName);
	void createFollowRequest(String startNodeUsername, String endNodeUsername);
	void deleteFollowRequest(String startNodeUsername, String endNodeUsername);
	Collection<User> getSendRequests(String username);
	Collection<User> getReceivedRequests(String username);
	LocalDateTime getTimeStampOfRequest(String startNodeUsername, String endNodeUsername);
	
}
