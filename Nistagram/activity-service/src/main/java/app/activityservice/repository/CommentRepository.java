package app.activityservice.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.jpa.repository.JpaRepository;

import app.activityservice.enums.PostType;
import app.activityservice.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	Collection<Comment> findAllByPostId(long postId);

	@Query("select c from Comment c where c.postId = ?1 and c.postType = ?2")
	Collection<Comment> findAllByPostIdAndPostType(long postId, PostType postType);
	
}
