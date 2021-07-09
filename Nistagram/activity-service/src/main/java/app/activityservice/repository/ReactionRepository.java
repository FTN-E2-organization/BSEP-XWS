package app.activityservice.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.activityservice.model.Reaction;

public interface ReactionRepository extends JpaRepository<Reaction, Long>{

	@Query(value = "select * from reaction r, profile p where p.username = ?1 and r.post_id = ?2 and p.id = r.owner_id and r.post_type = ?3 limit 1", nativeQuery = true)
	Reaction findByUsernameAndPostIDAndPostType(String ownerUsername, long postId, int postType);

	@Query(value = "select * from reaction r where r.post_id = ?1 and r.reaction_type = 0 and r.post_type = ?2", nativeQuery = true)
	Collection<Reaction> findLikesByPostIdAndPostType(long postId, int postType);

	@Query(value = "select * from reaction r where r.post_id = ?1 and r.reaction_type = 1 and r.post_type = ?2", nativeQuery = true)
	Collection<Reaction> findDislikesByPostIdAndPostType(long postId, int postType);
	
	@Query(value = "select * from reaction r, profile p where p.username = ?1 and r.reaction_type = 0 and r.owner_id = p.id and r.post_type = 0", nativeQuery = true)
	Collection<Reaction> findLikesByUsername(String username);
	
	@Query(value = "select * from reaction r, profile p where p.username = ?1 and r.reaction_type = 1 and r.owner_id = p.id and r.post_type = 0", nativeQuery = true)
	Collection<Reaction> findDislikesByUsername(String username);
	
}
