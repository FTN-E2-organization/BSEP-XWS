package app.java.zuulserver.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.java.zuulserver.client.AuthClient;
import app.java.zuulserver.client.FollowingClient;
import app.java.zuulserver.dto.ProfileDTO;
import app.java.zuulserver.dto.ProfileOverviewDTO;

@RestController
@RequestMapping(value = "api/aggregation")
public class AggregationController {
	
	private FollowingClient followingClient;
	private AuthClient authClient;
	
	@Autowired
	public AggregationController(FollowingClient followingClient, AuthClient authClient) {
		this.followingClient = followingClient;
		this.authClient = authClient;
	}
	
	@GetMapping("/profile-overview/{username}")
	public ResponseEntity<?> findAllFollowing(@PathVariable String username){
		
		try {
			ProfileOverviewDTO profileOverviewDTO = this.authClient.getProfile(username);
			Collection<String> followers = new ArrayList<>();
			Collection<String> following = new ArrayList<>();
			Collection<ProfileDTO> profileFollowingDTOs = this.followingClient.getFollowing(username);
			for(ProfileDTO f: profileFollowingDTOs) {
				following.add(f.username);
			}
			Collection<ProfileDTO> profileFollowersDTOs = this.followingClient.getFollowers(username);
			for(ProfileDTO f: profileFollowersDTOs) {
				followers.add(f.username);
			}
			profileOverviewDTO.followers = followers;
			profileOverviewDTO.following = following;
		
			
			return new ResponseEntity<ProfileOverviewDTO>(profileOverviewDTO, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
