package app.activityservice.service;

import app.activityservice.dto.AddCommentDTO;

public interface CommentService {

	void create(AddCommentDTO commentDTO);

}
