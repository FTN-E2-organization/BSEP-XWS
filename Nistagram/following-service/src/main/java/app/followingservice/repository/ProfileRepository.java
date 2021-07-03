package app.followingservice.repository;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import app.followingservice.model.FollowRequest;
import app.followingservice.model.Friendship;
import app.followingservice.model.Profile;

public interface ProfileRepository extends Neo4jRepository<Profile, Long> {
	
	@Query("MATCH (n:Profile) RETURN n")
    Collection<Profile> getAllProfiles();
	
	@Query("MATCH (n:Profile) WHERE n.username = $0 RETURN n")
    Profile getProfileByUsername(String username);
	
	@Query("MATCH (n:Profile{username:$0})-[:FOLLOW]->(f:Profile) RETURN f")
	Collection<Profile> getFollowing(String username);
	
	@Query("MATCH (n:Profile{username:$0})<-[:FOLLOW]-(f:Profile) RETURN f")
	Collection<Profile> getFollowers(String username);
	
	@Query("MATCH (a:Profile),(b:Profile) WHERE a.username = $0 AND b.username = $1 CREATE (a)-[r:FOLLOW {isMuted:false, isClose:false, activePostNotification:false, activeStoryNotification:false, activeLikesNotification:false, activeCommentNotification:false, activeMessageNotification:false}]->(b)")
	void createNewFriendship(String startNodeUsername, String endNodeUsername);
	
	@Query("MATCH (a:Profile),(b:Profile) WHERE a.username = $0 AND b.username = $1 MATCH (a)-[r:FOLLOW]->(b) DELETE r")
	void deleteFriendship(String startNodeUsername, String endNodeUsername);
	
	@Query("MATCH (a:Profile),(b:Profile) WHERE a.username = $0 AND b.username = $1 MATCH (a)-[r:FOLLOW]->(b) SET r.isMuted= $2")
	void setMuted(String Profile, String endNodeUsername, boolean isMuted);
	
	@Query("MATCH (a:Profile),(b:Profile) WHERE a.username = $0 AND b.username = $1 MATCH (a)-[r:FOLLOW]->(b) SET r.isClose= $2")
	void setClose(String startNodeUsername, String endNodeUsername, boolean isClose);
	
	@Query("MATCH (a:Profile),(b:Profile) WHERE a.username = $0 AND b.username = $1 MATCH (a)-[r:FOLLOW]->(b) SET r.activePostNotification= $2")
	void setActivePostNotification(String startNodeUsername, String endNodeUsername, boolean isActivePostNotification);
	
	@Query("MATCH (a:Profile),(b:Profile) WHERE a.username = $0 AND b.username = $1 MATCH (a)-[r:FOLLOW]->(b) SET r.activeStoryNotification= $2")
	void setActiveStoryNotification(String startNodeUsername, String endNodeUsername, boolean isActiveStoryNotification);

	@Query("MATCH (a:Profile) WHERE a.username = $0 DETACH DELETE a")
	void deleteProfile(String username);
	
	@Query("MATCH (n:Profile)-->(f:ProfileCategory{name:$0}) RETURN n")
	Collection<Profile> getProfilesByCategoryName(String categoryName);
	
	@Query("MATCH (a:Profile),(b:Profile) WHERE a.username = $0 AND b.username = $1 CREATE (a)-[r:REQUEST {timestamp:datetime()}]->(b)")
	void createFollowRequest(String startNodeUsername, String endNodeUsername);
	
	@Query("MATCH (a:Profile),(b:Profile) WHERE a.username = $0 AND b.username = $1 MATCH (a)-[r:REQUEST]->(b) DELETE r")
	void deleteFollowRequest(String startNodeUsername, String endNodeUsername);
	
	@Query("MATCH (n:Profile{username:$0})-[:REQUEST]->(f:Profile) RETURN f")
	Collection<Profile> getSendRequests(String username);
	
	@Query("MATCH (n:Profile{username:$0})<-[:REQUEST]-(f:Profile) RETURN f")
	Collection<Profile> getReceivedRequests(String username);
	
	@Query("MATCH (a:Profile),(b:Profile) WHERE a.username = $0 AND b.username = $1 MATCH (a)-[r:REQUEST]-(b) RETURN r.timestamp")
	LocalDateTime getTimeStampOfRequest(String startNodeUsername, String endNodeUsername);
	
	@Query("MATCH (a:Profile),(b:Profile) WHERE a.username = $0 AND b.username = $1 MATCH (a)-[r:FOLLOW]->(b) RETURN r.isClose")
	boolean getClose(String startNodeUsername, String endNodeUsername);
	
	@Query("MATCH (a:Profile),(b:Profile) WHERE a.username = $0 AND b.username = $1 MATCH (a)-[r:FOLLOW]->(b) RETURN r.isMuted")
	boolean getMuted(String startNodeUsername, String endNodeUsername);
	
	@Query("MATCH (a:Profile),(b:Profile) WHERE a.username = $0 AND b.username = $1 MATCH (a)-[r:FOLLOW]->(b) RETURN r.activePostNotification")
	boolean getActivePostNotification(String startNodeUsername, String endNodeUsername);
	
	@Query("MATCH (a:Profile),(b:Profile) WHERE a.username = $0 AND b.username = $1 MATCH (a)-[r:FOLLOW]->(b) RETURN r.activeStoryNotification")
	boolean getActiveStoryNotification(String startNodeUsername, String endNodeUsername);

	@Query("MATCH (a:Profile),(b:Profile) WHERE a.username = $0 AND b.username = $1 CREATE (a)-[r:BLOCK {isMuted:false, isClose:false, activePostNotification:false, activeStoryNotification:false}]->(b)")
	void createBlocking(String startNodeUsername, String endNodeUsername);
	
	@Query("MATCH (a:Profile),(b:Profile) WHERE a.username = $0 AND b.username = $1 MATCH (a)-[r:BLOCK]->(b) DELETE r")
	void deleteBlocking(String startNodeUsername, String endNodeUsername);
	
	@Query("MATCH (n:Profile{username:$0})-[:BLOCK]->(f:Profile) RETURN f")
	Collection<Profile> getBlockedProfiles(String username);	
	
	@Query("MATCH (n:Profile{username:$0})<-[:BLOCK]-(f:Profile) RETURN f")
	Collection<Profile> getBlockingProfiles(String username);	
	
	@Query("MATCH (n {username:$0}) SET n.isBlocked = true")
	void blockProfile(String username);
	
	@Query("MATCH (a:Profile),(b:Profile) WHERE a.username = $0 AND b.username = $1 MATCH (a)-[r:FOLLOW]->(b) RETURN r.activeLikesNotification")
	boolean getActiveLikesNotification(String startNodeUsername, String endNodeUsername);

	@Query("MATCH (a:Profile),(b:Profile) WHERE a.username = $0 AND b.username = $1 MATCH (a)-[r:FOLLOW]->(b) RETURN r.activeCommentNotification")
	boolean getActiveCommentsNotification(String startNodeUsername, String endNodeUsername);	
	
	@Query("MATCH (a:Profile),(b:Profile) WHERE a.username = $0 AND b.username = $1 MATCH (a)-[r:FOLLOW]->(b) SET r.activeLikesNotification= $2")
	void setActiveLikesNotification(String startNodeUsername, String endNodeUsername, boolean isActiveLikesNotification);
	
	@Query("MATCH (a:Profile),(b:Profile) WHERE a.username = $0 AND b.username = $1 MATCH (a)-[r:FOLLOW]->(b) SET r.activeCommentNotification= $2")
	void setActiveCommentNotification(String startNodeUsername, String endNodeUsername, boolean isActiveCommentNotification);
	
	@Query("MATCH (a:Profile),(b:Profile) WHERE a.username = $0 AND b.username = $1 MATCH (a)-[r:FOLLOW]->(b) SET r.activeMessageNotification= $2")
	void setActiveMessageNotification(String startNodeUsername, String endNodeUsername, boolean isActiveMessageNotification);

	@Query("MATCH (a:Profile),(b:Profile) WHERE a.username = $0 AND b.username = $1 MATCH (a)-[r:FOLLOW]->(b) RETURN r.activeMessageNotification")
	boolean getActiveMessageNotification(String startNodeUsername, String endNodeUsername);
	
	@Query("MATCH (a:Profile),(b:Profile) WHERE a.username = $0 AND b.username = $1 MATCH (a)-[r:FOLLOW]->(b) RETURN r")
	Friendship isFriendship(String startNodeUsername, String endNodeUsername);
	
	@Query("MATCH (a:Profile),(b:Profile) WHERE a.username = $0 AND b.username = $1 MATCH (a)-[r:REQUEST]->(b) RETURN r")
	FollowRequest isFollowRequest(String startNodeUsername, String endNodeUsername);
}


