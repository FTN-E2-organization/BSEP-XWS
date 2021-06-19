package app.followingservice.controller;

import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.followingservice.dto.NotificationsSettingsDTO;
import app.followingservice.dto.ProfileDTO;
import app.followingservice.model.CustomPrincipal;
import app.followingservice.service.ProfileService;

@RestController
@RequestMapping(value = "api/following/profile")
public class ProfileController {
	private ProfileService profileService;
	
	@Autowired
	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}
	
	@PostMapping
	public ResponseEntity<?> createRegularUser(@RequestBody ProfileDTO profileDTO) {
		try {
			profileService.addProfile(profileDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while creating user.", HttpStatus.BAD_REQUEST);
		}
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
		try {
			profileService.createNewFriendship(username1, username2);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<String>("An error occurred while creating new friendship.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('deleteFriendship')")
	@PutMapping("/delete-friendship/{username2}")
	public ResponseEntity<?> deleteFriendship(@PathVariable String username2){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
	        String username1 = principal.getUsername();
			profileService.deleteFriendship(username1, username2);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<String>("An error occurred while deleting friendship.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('mutedOrCloseFriendship')")
	@PutMapping("/muted/{username2}/{muted}")
	public ResponseEntity<?> setMuted(@PathVariable String username2, @PathVariable boolean muted){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
	        String username1 = principal.getUsername();
			profileService.setMuted(username1, username2, muted);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<String>("An error occurred while sending request for muting.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('mutedOrCloseFriendship')")
	@PutMapping("/close/{username2}/{close}")
	public ResponseEntity<?> setClose(@PathVariable String username2, @PathVariable boolean close){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
	        String username1 = principal.getUsername();
			profileService.setClose(username1, username2, close);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<String>("An error occurred while sending request for close friend.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('postOrStoryNotification')")
	@PutMapping("/post/{username2}/{post}")
	public ResponseEntity<?> setActivePostNotification(@PathVariable String username2, @PathVariable boolean post){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
	        String username1 = principal.getUsername();
			profileService.setActivePostNotification(username1, username2, post);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<String>("An error occurred while setting post notification.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('postOrStoryNotification')")
	@PutMapping("/story/{username2}/{story}")
	public ResponseEntity<?> setActiveStoryNotification(@PathVariable String username2, @PathVariable boolean story){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
	        String username1 = principal.getUsername();
			profileService.setActiveStoryNotification(username1, username2, story);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<String>("An error occurred while setting story notification.", HttpStatus.BAD_REQUEST);
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
			return new ResponseEntity<String>("An error occurred while deleting profile.", HttpStatus.BAD_REQUEST);
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
	@PutMapping("/create-request/{username2}")
	public ResponseEntity<?> createFollowRequest(@PathVariable String username2){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
	        String username1 = principal.getUsername();
			profileService.createFollowRequest(username1, username2);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<String>("An error occurred while creating follow request.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('deleteFollowRequest')")
	@PutMapping("/delete-request/{username1}/{username2}")
	public ResponseEntity<?> deleteFollowRequest(@PathVariable String username1, @PathVariable String username2){
		try {
			profileService.deleteFollowRequest(username1, username2);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<String>("An error occurred while deleting follow request.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('getRequests')")
	@GetMapping("/send-requests")
	public ResponseEntity<?> findSendRequests(){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
	        String username = principal.getUsername();
			Collection<ProfileDTO> profileDTOs = profileService.getSendRequests(username);
			return new ResponseEntity<Collection<ProfileDTO>>(profileDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAuthority('getRequests')")
	@GetMapping("/received-requests")
	public ResponseEntity<?> findReceivedRequests(){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
	        String username = principal.getUsername();
			Collection<ProfileDTO> profileDTOs = profileService.getReceivedRequests(username);
			return new ResponseEntity<Collection<ProfileDTO>>(profileDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAuthority('getRequests')")
	@GetMapping("/timestamp-request/{username2}")
	public ResponseEntity<?> findReceivedRequests(@PathVariable String username2){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
	        String username1 = principal.getUsername();
		LocalDateTime timestamp = profileService.getTimeStampOfRequest(username1, username2);
			return new ResponseEntity<LocalDateTime>(timestamp, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAuthority('mutedOrCloseFriendship')")
	@GetMapping("/muted/{username2}")
	public ResponseEntity<?> getMuted(@PathVariable String username2){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
	        String username1 = principal.getUsername();
			boolean isMuted = profileService.getMuted(username1, username2);
			return new ResponseEntity<Boolean>(isMuted, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<String>("An error occurred while getting muted friends.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('mutedOrCloseFriendship')")
	@GetMapping("/close/{username2}")
	public ResponseEntity<?> getClose(@PathVariable String username2){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
	        String username1 = principal.getUsername();
			boolean isClose = profileService.getClose(username1, username2);
			return new ResponseEntity<Boolean>(isClose, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<String>("An error occurred while getting close friends.", HttpStatus.BAD_REQUEST);
		}
	}
	
//	@PreAuthorize("hasAuthority('postOrStoryNotification')")
	@GetMapping("/post/{username1}/{username2}")
	public ResponseEntity<?> getActivePostNotification(@PathVariable String username1, @PathVariable String username2) {
		try {
//			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
//	        String username1 = principal.getUsername();
			boolean isActive = profileService.getActivePostNotification(username1, username2);
			return new ResponseEntity<Boolean>(isActive, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<String>("An error occurred while getting active post notification. - " + exception.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
//	@PreAuthorize("hasAuthority('postOrStoryNotification')")
	@GetMapping("/story//{username1}/{username2}")
	public ResponseEntity<?> getActiveStoryNotification(@PathVariable String username1, @PathVariable String username2) {
		try {
//			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
//	        String username1 = principal.getUsername();
			boolean isActive = profileService.getActiveStoryNotification(username1, username2);
			return new ResponseEntity<Boolean>(isActive, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<String>("An error occurred while getting active story notification. - " + exception.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('blockOrUnblockProfiles')")
	@PutMapping("/block/{username2}")
	public ResponseEntity<?> createBlocking(@PathVariable String username2){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
	        String username1 = principal.getUsername();
			profileService.createBlocking(username1, username2);
			profileService.deleteFriendship(username1, username2);
			profileService.deleteFriendship(username2, username1);
			profileService.deleteFollowRequest(username1, username2);
			profileService.deleteFollowRequest(username2, username1);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<String>("An error occurred while blocking.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('blockOrUnblockProfiles')")
	@PutMapping("/unblock/{username2}")
	public ResponseEntity<?> deleteBlocking(@PathVariable String username2){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
	        String username1 = principal.getUsername();
			profileService.deleteBlocking(username1, username2);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<String>("An error occurred while deleting blocking.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('blockOrUnblockProfiles')")
	@GetMapping("/blocked/{username}")
	public ResponseEntity<?> findBlockedProfiles(@PathVariable String username){
		
		try {
			Collection<ProfileDTO> profileDTOs =  profileService.getBlockedProfiles(username);
			return new ResponseEntity<Collection<ProfileDTO>>(profileDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/unmute-following/{username}")
	public ResponseEntity<?> findUnmuteFollowingByUsername(@PathVariable String username){

		try {
			Collection<ProfileDTO> profileDTOs = profileService.getUnmuteFollowingByUsername(username);
			return new ResponseEntity<Collection<ProfileDTO>>(profileDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/like-notification/{username1}/{username2}")
	public ResponseEntity<?> getActiveLikesNotification(@PathVariable String username1, @PathVariable String username2){
		try {
			boolean isActive = profileService.getActiveLikesNotification(username1, username2);
			return new ResponseEntity<Boolean>(isActive, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<String>("An error occurred while getting active like notification. - " + exception.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/comment-notification/{username1}/{username2}")
	public ResponseEntity<?> getActiveCommentsNotification(@PathVariable String username1, @PathVariable String username2){
		try {
			boolean isActive = profileService.getActiveCommentsNotification(username1, username2);
			return new ResponseEntity<Boolean>(isActive, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<String>("An error occurred while getting active comment notification. - " + exception.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/message-notification/{username1}/{username2}")
	public ResponseEntity<?> getActiveMessageNotification(@PathVariable String username1, @PathVariable String username2) {
		try {
			boolean isActive = profileService.getActiveMessageNotification(username1, username2);
			return new ResponseEntity<Boolean>(isActive, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<String>("An error occurred while getting active message notification. - " + exception.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@PreAuthorize("hasAuthority('postOrStoryNotification')")
	@PostMapping("/notification-settings")
	public ResponseEntity<?> setNotifications(@RequestBody NotificationsSettingsDTO notificationsSettingsDTO) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
	        notificationsSettingsDTO.loggedInUsername = principal.getUsername();
	        profileService.setNotifications(notificationsSettingsDTO);			
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while setting notifications. " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('postOrStoryNotification')")
	@GetMapping("/notification-settings/{username2}")
	public ResponseEntity<?> getNotificationsSettings(@PathVariable String username2) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
	        String username1 = principal.getUsername();
	        NotificationsSettingsDTO notificationsSettingsDTO = profileService.getNotificationsSettings(username1, username2);
			return new ResponseEntity<NotificationsSettingsDTO>(notificationsSettingsDTO, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<String>("An error occurred while getting notifications settings. - " + exception.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	
}
