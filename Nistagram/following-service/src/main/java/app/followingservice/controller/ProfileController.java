package app.followingservice.controller;

import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import app.followingservice.dto.ProfileDTO;
import app.followingservice.service.ProfileService;

@RestController
@RequestMapping(value = "api/following/profile")
public class ProfileController {
	private ProfileService profileService;
	
	@Autowired
	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}

	@GetMapping("/all")
	public ResponseEntity<?> findAllProfiles(){
		
		try {
			Collection<ProfileDTO> profileDTOs = profileService.getAllProfiles();
			return new ResponseEntity<Collection<ProfileDTO>>(profileDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<?> findProfileByUsername(@PathVariable String username){
		
		try {
			ProfileDTO profileDTO = profileService.getProfileByUsername(username);
			return new ResponseEntity<ProfileDTO>(profileDTO, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/following/{username}")
	public ResponseEntity<?> findAllFollowing(@PathVariable String username){
		
		try {
			Collection<ProfileDTO> profileDTOs = profileService.getFollowingByUsername(username);
			return new ResponseEntity<Collection<ProfileDTO>>(profileDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/followers/{username}")
	public ResponseEntity<?> findAllFollowers(@PathVariable String username){
		
		try {
			Collection<ProfileDTO> profileDTOs =  profileService.getFollowersByUsername(username);
			return new ResponseEntity<Collection<ProfileDTO>>(profileDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAuthority('createFriendship')")
	@PutMapping("/create-friendship/{username1}/{username2}")
	public ResponseEntity<?> createNewFriendship(@PathVariable String username1, @PathVariable String username2){
		/*Obratiti paznju na to da ce se jedan username kupiti iz tokena pa prepraviti*/
		try {
			profileService.createNewFriendship(username1, username2);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('deleteFriendship')")
	@PutMapping("/delete-friendship/{username1}/{username2}")
	public ResponseEntity<?> deleteFriendship(@PathVariable String username1, @PathVariable String username2){
		/*Obratiti paznju na to da ce se jedan username kupiti iz tokena pa prepraviti*/
		try {
			profileService.deleteFriendship(username1, username2);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('mutedOrCloseFriendship')")
	@PutMapping("/muted/{username1}/{username2}/{muted}")
	public ResponseEntity<?> setMuted(@PathVariable String username1, @PathVariable String username2, @PathVariable boolean muted){
		/*Obratiti paznju na to da ce se jedan username kupiti iz tokena pa prepraviti*/
		try {
			profileService.setMuted(username1, username2, muted);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('mutedOrCloseFriendship')")
	@PutMapping("/close/{username1}/{username2}/{close}")
	public ResponseEntity<?> setClose(@PathVariable String username1, @PathVariable String username2, @PathVariable boolean close){
		/*Obratiti paznju na to da ce se jedan username kupiti iz tokena pa prepraviti*/
		try {
			profileService.setClose(username1, username2, close);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('postOrStoryNotification')")
	@PutMapping("/post/{username1}/{username2}/{post}")
	public ResponseEntity<?> setActivePostNotification(@PathVariable String username1, @PathVariable String username2, @PathVariable boolean post){
		/*Obratiti paznju na to da ce se jedan username kupiti iz tokena pa prepraviti*/
		try {
			profileService.setActivePostNotification(username1, username2, post);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('postOrStoryNotification')")
	@PutMapping("/story/{username1}/{username2}/{story}")
	public ResponseEntity<?> setActiveStoryNotification(@PathVariable String username1, @PathVariable String username2, @PathVariable boolean story){
		/*Obratiti paznju na to da ce se jedan username kupiti iz tokena pa prepraviti*/
		try {
			profileService.setActiveStoryNotification(username1, username2, story);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('deleteProfile')")
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
			Collection<ProfileDTO> profileDTOs = profileService.getProfilesByCategoryName(name);
			return new ResponseEntity<Collection<ProfileDTO>>(profileDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAuthority('createFriendship')")
	@PutMapping("/create-request/{username1}/{username2}")
	public ResponseEntity<?> createFollowRequest(@PathVariable String username1, @PathVariable String username2){
		/*Obratiti paznju na to da ce se jedan username kupiti iz tokena pa prepraviti*/
		try {
			profileService.createFollowRequest(username1, username2);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('deleteFollowRequest')")
	@PutMapping("/delete-request/{username1}/{username2}")
	public ResponseEntity<?> deleteFollowRequest(@PathVariable String username1, @PathVariable String username2){
		/*Obratiti paznju na to da ce se jedan username kupiti iz tokena pa prepraviti*/
		try {
			profileService.deleteFollowRequest(username1, username2);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('getRequests')")
	@GetMapping("/send-requests/{username}")
	public ResponseEntity<?> findSendRequests(@PathVariable String username){
		/*Obratiti paznju na to da ce se username kupiti iz tokena pa prepraviti*/
		try {
			Collection<ProfileDTO> profileDTOs = profileService.getSendRequests(username);
			return new ResponseEntity<Collection<ProfileDTO>>(profileDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAuthority('getRequests')")
	@GetMapping("/received-requests/{username}")
	public ResponseEntity<?> findReceivedRequests(@PathVariable String username){
		/*Obratiti paznju na to da ce se username kupiti iz tokena pa prepraviti*/
		try {
			Collection<ProfileDTO> profileDTOs = profileService.getReceivedRequests(username);
			return new ResponseEntity<Collection<ProfileDTO>>(profileDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAuthority('getRequests')")
	@GetMapping("/timestamp-request/{username1}/{username2}")
	public ResponseEntity<?> findReceivedRequests(@PathVariable String username1, @PathVariable String username2){
		/*Obratiti paznju na to da ce se jedan username kupiti iz tokena pa prepraviti*/
		try {
		LocalDateTime timestamp = profileService.getTimeStampOfRequest(username1, username2);
			return new ResponseEntity<LocalDateTime>(timestamp, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAuthority('mutedOrCloseFriendship')")
	@GetMapping("/muted/{username1}/{username2}")
	public ResponseEntity<?> getMuted(@PathVariable String username1, @PathVariable String username2){
		/*Obratiti paznju na to da ce se jedan username kupiti iz tokena pa prepraviti*/
		try {
			boolean isMuted = profileService.getMuted(username1, username2);
			return new ResponseEntity<Boolean>(isMuted, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('mutedOrCloseFriendship')")
	@GetMapping("/close/{username1}/{username2}")
	public ResponseEntity<?> getClose(@PathVariable String username1, @PathVariable String username2){
		/*Obratiti paznju na to da ce se jedan username kupiti iz tokena pa prepraviti*/
		try {
			boolean isClose = profileService.getClose(username1, username2);
			return new ResponseEntity<Boolean>(isClose, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('postOrStoryNotification')")
	@GetMapping("/post/{username1}/{username2}")
	public ResponseEntity<?> getActivePostNotification(@PathVariable String username1, @PathVariable String username2){
		/*Obratiti paznju na to da ce se jedan username kupiti iz tokena pa prepraviti*/
		try {
			boolean isActive = profileService.getActivePostNotification(username1, username2);
			return new ResponseEntity<Boolean>(isActive, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('postOrStoryNotification')")
	@GetMapping("/story/{username1}/{username2}")
	public ResponseEntity<?> getActiveStoryNotification(@PathVariable String username1, @PathVariable String username2){
		/*Obratiti paznju na to da ce se jedan username kupiti iz tokena pa prepraviti*/
		try {
			boolean isActive = profileService.getActiveStoryNotification(username1, username2);
			return new ResponseEntity<Boolean>(isActive, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
