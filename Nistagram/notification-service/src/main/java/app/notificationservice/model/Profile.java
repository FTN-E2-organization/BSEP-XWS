package app.notificationservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Profile {
	
	@Id
	private String idMongo;
	
	private String username;
	
	@JsonProperty
	private boolean isBlocked;
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Profile) {
			return ((Profile) o).getUsername().equals(getUsername());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return idMongo.hashCode();
	}	
	
	
}
