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
		// TODO Auto-generated method stub
		return userRepository.getAllUsers();

	}

}
