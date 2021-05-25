package app.followingservice.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import app.followingservice.dto.UserDTO;
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
	public ResponseEntity<?> findAllUsers(){
		
		try {
			Collection<User> users = userService.getAllUsers();
			return new ResponseEntity<Collection<User>>(users, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/following/{username}")
	public ResponseEntity<?> findAllFollowing(@PathVariable String username){
		
		try {
			Collection<User> users = userService.getFollowingByUsername(username);
			return new ResponseEntity<Collection<User>>(users, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/followers/{username}")
	public ResponseEntity<?> findAllFollowers(@PathVariable String username){
		
		try {
			Collection<User> users = userService.getFollowersByUsername(username);
			return new ResponseEntity<Collection<User>>(users, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/create-friendship/{username1}/{username2}")
	public ResponseEntity<?> createNewFriendship(@PathVariable String username1, @PathVariable String username2){
		
		try {
			userService.createNewFriendship(username1, username2);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/delete-friendship/{username1}/{username2}")
	public ResponseEntity<?> deleteFriendship(@PathVariable String username1, @PathVariable String username2){
		
		try {
			userService.deleteFriendship(username1, username2);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/muted/{username1}/{username2}/{muted}")
	public ResponseEntity<?> setMuted(@PathVariable String username1, @PathVariable String username2, @PathVariable boolean muted){
		
		try {
			userService.setMuted(username1, username2, muted);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/close/{username1}/{username2}/{close}")
	public ResponseEntity<?> setClose(@PathVariable String username1, @PathVariable String username2, @PathVariable boolean close){
		
		try {
			userService.setClose(username1, username2, close);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/post/{username1}/{username2}/{post}")
	public ResponseEntity<?> setActivePostNotification(@PathVariable String username1, @PathVariable String username2, @PathVariable boolean post){
		
		try {
			userService.setActivePostNotification(username1, username2, post);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/story/{username1}/{username2}/{story}")
	public ResponseEntity<?> setActiveStoryNotification(@PathVariable String username1, @PathVariable String username2, @PathVariable boolean story){
		
		try {
			userService.setActiveStoryNotification(username1, username2, story);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(path = "/create", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<?> addNewUser(@RequestBody UserDTO userDTO){
		
		try {
			userService.addNewUser(userDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/delete/{username}")
	public ResponseEntity<?> deleteUser(@PathVariable String username){
		
		try {
			userService.deleteUser(username);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/target-group/{name}")
	public ResponseEntity<?> findTargetGroupOfUsers(@PathVariable String name){
		
		try {
			Collection<User> users = userService.getUsersByCategoryName(name);
			return new ResponseEntity<Collection<User>>(users, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
