package app.followingservice.repository;

import java.util.Collection;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import app.followingservice.model.ProfileCategory;

public interface ProfileCategoryRepository extends Neo4jRepository<ProfileCategory, Long>{
	
	@Query("MATCH (n:ProfileCategory) RETURN n")
    Collection<ProfileCategory> getAllProfileCategoryies();
	
	@Query("MATCH (n:User{username:$0})-->(f:ProfileCategory) Return f")
	Collection<ProfileCategory> getProfileCategoriesByUsername(String username);

	@Query("MATCH (a:User),(b:ProfileCategory) WHERE a.username = $0 AND b.name = $1 CREATE (a)-[r:INTERESTED]->(b)")
	void addUsersProfileCategory(String username, String categoryName);
	
}
