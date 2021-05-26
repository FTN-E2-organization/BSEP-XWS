package app.followingservice.service;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.followingservice.dto.UserDTO;
import app.followingservice.model.User;
import app.followingservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	private UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public Collection<User> getAllUsers() {
		return userRepository.getAllUsers();

	}

	@Override
	public Collection<User> getFollowingByUsername(String username) {
		return userRepository.getFollowing(username);
	}

	@Override
	public Collection<User> getFollowersByUsername(String username) {
		return userRepository.getFollowers(username);
	}

	@Override
	public void createNewFriendship(String startNodeUsername, String endNodeUsername) {
		userRepository.createNewFriendship(startNodeUsername, endNodeUsername);
	}

	@Override
	public void deleteFriendship(String startNodeUsername, String endNodeUsername) {
		userRepository.deleteFriendship(startNodeUsername, endNodeUsername);
	}

	@Override
	public void setMuted(String startNodeUsername, String endNodeUsername, boolean isMuted) {
		userRepository.setMuted(startNodeUsername, endNodeUsername, isMuted);
	}

	@Override
	public void setClose(String startNodeUsername, String endNodeUsername, boolean isClose) {
		userRepository.setClose(startNodeUsername, endNodeUsername, isClose);
	}

	@Override
	public void setActivePostNotification(String startNodeUsername, String endNodeUsername,
			boolean isActivePostNotification) {
		userRepository.setActivePostNotification(startNodeUsername, endNodeUsername, isActivePostNotification);
	}

	@Override
	public void setActiveStoryNotification(String startNodeUsername, String endNodeUsername,
			boolean isActiveStoryNotification) {
		userRepository.setActiveStoryNotification(startNodeUsername, endNodeUsername, isActiveStoryNotification);
	}

	@Override
	public void addNewUser(UserDTO userDTO) {
		User user = new User();
		user.setUsername(userDTO.username);
		
		userRepository.save(user);
	}

	@Override
	public void deleteUser(String username) {
		userRepository.deleteUser(username);
	}

	@Override
	public Collection<User> getUsersByCategoryName(String categoryName) {
		return userRepository.getUsersByCategoryName(categoryName);
	}

	@Override
	public void createFollowRequest(String startNodeUsername, String endNodeUsername) {
		userRepository.createFollowRequest(startNodeUsername, endNodeUsername);
	}

	@Override
	public void deleteFollowRequest(String startNodeUsername, String endNodeUsername) {
		userRepository.deleteFollowRequest(startNodeUsername, endNodeUsername);
	}

	@Override
	public Collection<User> getSendRequests(String username) {
		return userRepository.getSendRequests(username);
	}

	@Override
	public Collection<User> getReceivedRequests(String username) {
		return userRepository.getReceivedRequests(username);
	}

	@Override
	public LocalDateTime getTimeStampOfRequest(String startNodeUsername, String endNodeUsername) {
		return userRepository.getTimeStampOfRequest(startNodeUsername, endNodeUsername);
	}

}
