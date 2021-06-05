package app.publishingservice.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.publishingservice.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	@Query(value = "select * from post p, profile pr where p.profile_id=pr.id and pr.username = ?1 and pr.is_deleted=false and " + 
					" p.is_deleted=false ", nativeQuery = true)
	Collection<Post> findAllByUsername(String username);

	@Query(value = "select * from post p, profile pr, location l where p.profile_id=pr.id and pr.is_public=true and pr.is_deleted=false and " + 
			" p.is_deleted=false and l.name=?1 and l.id=p.location_id ", nativeQuery = true)
	Collection<Post> findAllByLocationName(String locationName);

}
