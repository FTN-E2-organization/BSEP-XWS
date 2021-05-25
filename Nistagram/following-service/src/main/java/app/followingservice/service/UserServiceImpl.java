package app.followingservice.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}