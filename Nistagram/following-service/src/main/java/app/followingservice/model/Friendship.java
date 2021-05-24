package app.followingservice.model;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "FOLLOW")
public class Friendship {
	private Long id;
	private boolean isMuted;
	private boolean isClose;
	private boolean activePostNotification;
	private boolean activeStoryNotification;
	@StartNode
    private User startNode;
    @EndNode
    private User endNode;
    
	public Long getId() {
		return id;
	}
	
	public boolean isMuted() {
		return isMuted;
	}
	
	public boolean isClose() {
		return isClose;
	}
	
	public boolean isActivePostNotification() {
		return activePostNotification;
	}
	
	public boolean isActiveStoryNotification() {
		return activeStoryNotification;
	}
	
	public User getStartNode() {
		return startNode;
	}
	
	public User getEndNode() {
		return endNode;
	}

}
