package app.followingservice.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@NodeEntity
public class ProfileCategory {
	@Id
    @GeneratedValue
	private Long id;
	private String name;
	
	public ProfileCategory() {
		
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	

}
