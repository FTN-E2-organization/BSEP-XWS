package app.java.zuulserver.client;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import app.java.zuulserver.dto.PostDTO;

@FeignClient(name = "publishing-service")
public interface PublishingClient {
	
	@GetMapping("api/publishing/post/{username}")
	Collection<PostDTO> getPostsByUsername(@PathVariable("username") String username);

}
