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
	
	@Query("MATCH (a:User),(b:User) WHERE a.username = $0 AND b.username = $1 CREATE (a)-[r:FOLLOW {isMuted:false, isClose:false, activePostNotification:false, activeStoryNotification:false}]->(b)")
	void createNewFriendship(String startNodeUsername, String endNodeUsername);
	
	@Query("MATCH (a:User),(b:User) WHERE a.username = $0 AND b.username = $1 MATCH (a)-[r:FOLLOW]->(b) DELETE r")
	void deleteFriendship(String startNodeUsername, String endNodeUsername);
	
}
