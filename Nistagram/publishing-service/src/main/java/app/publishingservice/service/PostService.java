package app.publishingservice.service;

import java.util.Collection;

import app.publishingservice.dto.AddPostDTO;
import app.publishingservice.model.Post;

public interface PostService {

	void create(AddPostDTO postDTO);
	Collection<Post> getAllByUsername(String username);

}
