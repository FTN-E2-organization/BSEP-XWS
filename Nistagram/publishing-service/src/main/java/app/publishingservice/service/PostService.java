package app.publishingservice.service;

import java.util.Collection;

import app.publishingservice.dto.PostDTO;
import app.publishingservice.model.Post;

public interface PostService {

	Long create(PostDTO postDTO);
	Collection<Post> getAllByUsername(String username);
	Post getById(long postId);

}
