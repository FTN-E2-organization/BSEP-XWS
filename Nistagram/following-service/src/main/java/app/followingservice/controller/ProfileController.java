package app.followingservice.controller;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import app.followingservice.dto.ProfileDTO;
import app.followingservice.model.Profile;
import app.followingservice.service.ProfileService;
import app.followingservice.validator.UserValidator;

@RestController
@RequestMapping(value = "api/profile")
public class ProfileController {
	private ProfileService profileService;
	
	@Autowired
	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}

	@GetMapping("/all")
	public ResponseEntity<?> findAllProfiles(){
		
		try {
			Collection<Profile> profiles = profileService.getAllProfiles();
			return new ResponseEntity<Collection<Profile>>(profiles, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/one/{username}")
	public ResponseEntity<?> findProfileByUsername(@PathVariable String username){
		
		try {
			Profile profile = profileService.getProfileByUsername(username);
			return new ResponseEntity<Profile>(profile, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/following/{username}")
	public ResponseEntity<?> findAllFollowing(@PathVariable String username){
		
		try {
			Collection<Profile> profiles = profileService.getFollowingByUsername(username);
			return new ResponseEntity<Collection<Profile>>(profiles, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/followers/{username}")
	public ResponseEntity<?> findAllFollowers(@PathVariable String username){
		
		try {
			Collection<Profile> profiles = profileService.getFollowersByUsername(username);
			return new ResponseEntity<Collection<Profile>>(profiles, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/create-friendship/{username1}/{username2}")
	public ResponseEntity<?> createNewFriendship(@PathVariable String username1, @PathVariable String username2){
		
		try {
			profileService.createNewFriendship(username1, username2);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/delete-friendship/{username1}/{username2}")
	public ResponseEntity<?> deleteFriendship(@PathVariable String username1, @PathVariable String username2){
		
		try {
			profileService.deleteFriendship(username1, username2);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/muted/{username1}/{username2}/{muted}")
	public ResponseEntity<?> setMuted(@PathVariable String username1, @PathVariable String username2, @PathVariable boolean muted){
		
		try {
			profileService.setMuted(username1, username2, muted);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/close/{username1}/{username2}/{close}")
	public ResponseEntity<?> setClose(@PathVariable String username1, @PathVariable String username2, @PathVariable boolean close){
		
		try {
			profileService.setClose(username1, username2, close);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/post/{username1}/{username2}/{post}")
	public ResponseEntity<?> setActivePostNotification(@PathVariable String username1, @PathVariable String username2, @PathVariable boolean post){
		
		try {
			profileService.setActivePostNotification(username1, username2, post);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/story/{username1}/{username2}/{story}")
	public ResponseEntity<?> setActiveStoryNotification(@PathVariable String username1, @PathVariable String username2, @PathVariable boolean story){
		
		try {
			profileService.setActiveStoryNotification(username1, username2, story);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(path = "/create", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<?> addProfile(@RequestBody ProfileDTO userDTO){
		
		try {
			UserValidator.validate(userDTO);
			profileService.addProfile(userDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		catch(Exception exception) {
			return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/delete/{username}")
	public ResponseEntity<?> deleteProfile(@PathVariable String username){
		
		try {
			profileService.deleteProfile(username);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/target-group/{name}")
	public ResponseEntity<?> findTargetGroupOfProfiles(@PathVariable String name){
		
		try {
			Collection<Profile> profiles = profileService.getProfilesByCategoryName(name);
			return new ResponseEntity<Collection<Profile>>(profiles, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/create-request/{username1}/{username2}")
	public ResponseEntity<?> createFollowRequest(@PathVariable String username1, @PathVariable String username2){
		
		try {
			profileService.createFollowRequest(username1, username2);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/delete-request/{username1}/{username2}")
	public ResponseEntity<?> deleteFollowRequest(@PathVariable String username1, @PathVariable String username2){
		
		try {
			profileService.deleteFollowRequest(username1, username2);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/send-requests/{username}")
	public ResponseEntity<?> findSendRequests(@PathVariable String username){
		
		try {
			Collection<Profile> profiles = profileService.getSendRequests(username);
			return new ResponseEntity<Collection<Profile>>(profiles, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/received-requests/{username}")
	public ResponseEntity<?> findReceivedRequests(@PathVariable String username){
		
		try {
			Collection<Profile> profiles = profileService.getReceivedRequests(username);
			return new ResponseEntity<Collection<Profile>>(profiles, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/timestamp-request/{username1}/{username2}")
	public ResponseEntity<?> findReceivedRequests(@PathVariable String username1, @PathVariable String username2){
		
		try {
		LocalDateTime timestamp = profileService.getTimeStampOfRequest(username1, username2);
			return new ResponseEntity<LocalDateTime>(timestamp, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}