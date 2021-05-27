package app.publishingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.publishingservice.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
