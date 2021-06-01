package app.activityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.activityservice.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	
	
}
