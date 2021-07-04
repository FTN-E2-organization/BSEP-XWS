package app.java.zuulserver.client;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import app.java.zuulserver.dto.ProfileCategoryDTO;
import app.java.zuulserver.dto.ProfileDTO;

@FeignClient(name = "following-service")
public interface FollowingClient {
	
	@GetMapping("api/following/profile/following/{username}")
    Collection<ProfileDTO> getFollowing(@PathVariable("username") String username);
	
	@GetMapping("api/following/profile/followers/{username}")
    Collection<ProfileDTO> getFollowers(@PathVariable("username") String username);
	
	@GetMapping("api/following/profile/unmute-following/{username}")
    Collection<ProfileDTO> getUnmuteFollowing(@PathVariable("username") String username);
	
	@GetMapping("api/following/profile/like-notification/{username1}/{username2}")
    Boolean getActiveLikesNotification(@PathVariable String username1, @PathVariable String username2);
	
	@GetMapping("api/following/profile/comment-notification/{username1}/{username2}")
    Boolean getActiveCommentsNotification(@PathVariable String username1, @PathVariable String username2);
	
	@GetMapping("api/following/profile/post/{username1}/{username2}")
    Boolean getActivePostNotification(@PathVariable String username1, @PathVariable String username2);

	@GetMapping("api/following/profile/story/{username1}/{username2}")
	boolean getActiveStoryNotification(@PathVariable String username1, @PathVariable String username2);
	
	@GetMapping("api/following/profile-category/{username}")
	Collection<ProfileCategoryDTO> findProfileCategoriesByUsername(@PathVariable("username") String username);
}
