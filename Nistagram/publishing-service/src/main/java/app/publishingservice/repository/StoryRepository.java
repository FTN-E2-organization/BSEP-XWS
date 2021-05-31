package app.publishingservice.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.publishingservice.model.Story;

public interface StoryRepository extends JpaRepository<Story, Long> {

	@Query(value = "select * from story s, profile pr where s.owner_id=pr.id and pr.username = ?1 and pr.is_deleted=false and " + 
			" s.is_deleted=false and is_highlight=true ", nativeQuery = true)	
	Collection<Story> findHighlightStoriesByUsername(String username);

}
