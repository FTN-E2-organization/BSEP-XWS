package app.publishingservice.service;

import java.util.Collection;

import app.publishingservice.dto.PostDTO;
import app.publishingservice.model.Post;

public interface PostService {

	Long create(PostDTO postDTO);
	Collection<Post> getAllByUsername(String username);
	Post getById(long postId) throws Exception;
	Collection<Post> getAllByLocationName(String locationName);
	void delete(long postId);
	Collection<Post> getAllByHashtagName(String hashtagName);

}
