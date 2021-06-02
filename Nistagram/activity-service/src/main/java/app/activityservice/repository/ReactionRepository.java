package app.activityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.activityservice.model.Reaction;

public interface ReactionRepository extends JpaRepository<Reaction, Long>{

//	Reaction findByUsernameAndPostID(String ownerUsername, long postId);

	
}
