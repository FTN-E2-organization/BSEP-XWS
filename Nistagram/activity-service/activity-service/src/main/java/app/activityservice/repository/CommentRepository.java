package app.activityservice.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import app.activityservice.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	Collection<Comment> findAllByPostId(long postId);
	
}
