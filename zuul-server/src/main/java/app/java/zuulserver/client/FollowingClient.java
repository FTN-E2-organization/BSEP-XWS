package app.java.zuulserver.client;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import app.java.zuulserver.dto.ProfileDTO;

@FeignClient(name = "following-service")
public interface FollowingClient {
	
	@GetMapping("api/following/profile/following/{username}")
    Collection<ProfileDTO> getFollowing(@PathVariable("username") String username);
	
	@GetMapping("api/following/profile/followers/{username}")
    Collection<ProfileDTO> getFollowers(@PathVariable("username") String username);
	
	@GetMapping("api/following/profile/unmute-following/{username}")
    Collection<ProfileDTO> getUnmuteFollowing(@PathVariable("username") String username);
}
