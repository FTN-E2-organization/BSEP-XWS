package app.followingservice.repository;

import java.util.Collection;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import app.followingservice.model.ProfileCategory;

public interface ProfileCategoryRepository extends Neo4jRepository<ProfileCategory, Long>{
	
	@Query("MATCH (n:ProfileCategory) RETURN n")
    Collection<ProfileCategory> getAllProfileCategory();

}
