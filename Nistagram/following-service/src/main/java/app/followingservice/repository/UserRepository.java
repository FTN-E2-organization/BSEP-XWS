package app.followingservice.repository;

import java.util.Collection;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import app.followingservice.model.User;

public interface UserRepository extends Neo4jRepository<User, Long> {
	
	@Query("MATCH (n:User) RETURN n")
    Collection<User> getAllUsers();
	
	@Query("MATCH (n:User{username:$0})-->(f:User) Return f")
	Collection<User> getFollowing(String username);
	
	@Query("MATCH (n:User{username:$0})<--(f:User) Return f")
	Collection<User> getFollowers(String username);
	
}
