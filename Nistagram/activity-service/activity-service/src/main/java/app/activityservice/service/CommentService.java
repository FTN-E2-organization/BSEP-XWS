package app.activityservice.service;

import java.util.Collection;

import app.activityservice.dto.AddCommentDTO;
import app.activityservice.model.Comment;

public interface CommentService {

	void create(AddCommentDTO commentDTO);

	Collection<Comment> findAllByPostId(long postId);

}
