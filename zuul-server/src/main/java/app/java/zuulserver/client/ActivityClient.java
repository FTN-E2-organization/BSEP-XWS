package app.java.zuulserver.client;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import app.java.zuulserver.dto.CommentDTO;
import app.java.zuulserver.dto.PostDTO;

@FeignClient(name = "activity-service")
public interface ActivityClient {

	@GetMapping("api/activity/comment/{postId}/post-id")
	Collection<CommentDTO> getCommentsByPostId(@PathVariable("postId") long postId);	
	
}
