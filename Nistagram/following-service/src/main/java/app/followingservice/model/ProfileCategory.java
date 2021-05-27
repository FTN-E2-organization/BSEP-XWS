package app.followingservice.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;

import lombok.Builder;
import lombok.Data;

@Data
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

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
