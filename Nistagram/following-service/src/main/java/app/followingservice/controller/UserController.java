package app.followingservice.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.followingservice.model.User;
import app.followingservice.service.UserService;

@RestController
@RequestMapping(value = "api/user")
public class UserController {
	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/all")
	public ResponseEntity<?> allUsers(){
		
		try {
			Collection<User> users = userService.getAllUsers();
			return new ResponseEntity<Collection<User>>(users, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
