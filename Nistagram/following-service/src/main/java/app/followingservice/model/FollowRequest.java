package app.followingservice.model;

import java.time.LocalDateTime;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;

import lombok.Builder;

@RelationshipEntity(type = "REQUEST")
@Builder
public class FollowRequest {
	@Id
    @GeneratedValue
	private Long id;
	@Property
	private LocalDateTime timestamp;
	
	public FollowRequest() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
