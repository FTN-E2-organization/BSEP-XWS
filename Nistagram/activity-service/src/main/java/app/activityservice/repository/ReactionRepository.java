package app.activityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.activityservice.model.Reaction;

public interface ReactionRepository extends JpaRepository<Reaction, Long>{

	@Query(value = "select * from reaction r, profile p where p.username = ?1 and r.post_id = ?2 and p.id = r.owner_id limit 1", nativeQuery = true)
	Reaction findByUsernameAndPostID(String ownerUsername, long postId);
	
}
