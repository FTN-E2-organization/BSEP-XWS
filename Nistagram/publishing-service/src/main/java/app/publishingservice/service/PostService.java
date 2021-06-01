package app.publishingservice.service;

import java.util.Collection;

import app.publishingservice.dto.PostDTO;
import app.publishingservice.model.Post;

public interface PostService {

	void create(PostDTO postDTO);
	Collection<Post> getAllByUsername(String username);

}
