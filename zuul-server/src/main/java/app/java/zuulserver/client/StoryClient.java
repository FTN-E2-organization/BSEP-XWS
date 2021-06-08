package app.java.zuulserver.client;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import app.java.zuulserver.dto.StoryDTO;

@FeignClient(name = "story-service")
public interface StoryClient {
	@GetMapping("api/story/all/profile/{username}")
    Collection<StoryDTO> getStoriesByUsername(@PathVariable("username") String username);
}
