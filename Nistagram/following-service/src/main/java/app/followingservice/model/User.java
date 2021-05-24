package app.followingservice.model;

import java.util.Set;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;

@NodeEntity
public class User {
	@Id
    @GeneratedValue
	private Long id;
	private String username;
	
	@Relationship(type = "FOLLOW")
    private Set<Friendship> friendships;
	
	public User() {
		
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

}
