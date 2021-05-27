package app.followingservice.model;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import lombok.Builder;

@RelationshipEntity(type = "FOLLOW")
@Builder
public class Friendship {
	@Id
    @GeneratedValue
	private Long id;
	@Property
	private boolean isMuted;
	@Property
	private boolean isClose;
	@Property
	private boolean activePostNotification;
	@Property
	private boolean activeStoryNotification;
	@StartNode
    private Profile startNode;
    @EndNode
    private Profile endNode;

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

	public Profile getStartNode() {
		return startNode;
	}

	public Profile getEndNode() {
		return endNode;
	}
	
}
