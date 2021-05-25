package app.followingservice.service;

import java.util.Collection;

import app.followingservice.model.User;

public interface UserService {

	Collection<User> getAllUsers();
	Collection<User> getFollowingByUsername(String username);
	Collection<User> getFollowersByUsername(String username);
	void createNewFriendship(String startNodeUsername, String endNodeUsername);
	void deleteFriendship(String startNodeUsername, String endNodeUsername);
}