package app.java.zuulserver.client;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import app.java.zuulserver.dto.PostDTO;
import app.java.zuulserver.dto.StoryDTO;

@FeignClient(name = "publishing-service")
public interface PublishingClient {
	
	@GetMapping("api/publishing/post/username/{username}")
	Collection<PostDTO> getPostsByUsername(@PathVariable("username") String username);
	
	@GetMapping("api/publishing/story/highlight/{username}")
	Collection<StoryDTO> getHighlightStoriesByUsername(@PathVariable("username") String username);

	@GetMapping("api/publishing/location")
	Collection<String> getLocations();
	
	@GetMapping("api/publishing/hashtag")
	Collection<String> getHashtags();
	
	@GetMapping("api/publishing/post/location/{locationName}")
	Collection<PostDTO> getPostsByLocationName(@PathVariable("locationName") String locationName);
}
