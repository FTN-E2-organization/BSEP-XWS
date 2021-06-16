package app.java.zuulserver.client;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import app.java.zuulserver.dto.ReactionDTO;

@FeignClient(name = "activity-service")
public interface ActivityClient {

	@GetMapping("api/activity/reaction/likes/by-username/{username}")
	Collection<ReactionDTO> getLikesByUsername(@PathVariable("username") String username);

	@GetMapping("api/activity/reaction/dislikes/by-username/{username}")
	Collection<ReactionDTO> getDislikesByUsername(@PathVariable("username") String username);
	
}
