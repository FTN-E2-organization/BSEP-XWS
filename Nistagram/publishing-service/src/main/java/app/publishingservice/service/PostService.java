package app.publishingservice.service;

import app.publishingservice.dto.AddPostDTO;

public interface PostService {

	void create(AddPostDTO postDTO);
	
}
