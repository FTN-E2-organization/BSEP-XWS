package app.java.zuulserver.client;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import app.java.zuulserver.dto.ReactionDTO;

@FeignClient(name = "activity-service")
public interface ActivityClient {

	@GetMapping("api/activity/reaction/likes/by-username")
	Collection<ReactionDTO> getLikesByUsername();	
	
}
