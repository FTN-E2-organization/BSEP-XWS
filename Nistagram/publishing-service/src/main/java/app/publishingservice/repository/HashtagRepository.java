package app.publishingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.publishingservice.model.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

	boolean existsByName(String name);
	
}
