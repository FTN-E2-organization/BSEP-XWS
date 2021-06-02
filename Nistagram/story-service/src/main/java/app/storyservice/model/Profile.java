package app.storyservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document
public class Profile {

	private String username;

	@Id
	private String idMongo;
	@JsonProperty
	private boolean isPublic;
	@JsonProperty
	private boolean isDeleted;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIdMongo() {
		return idMongo;
	}

	public void setIdMongo(String idMongo) {
		this.idMongo = idMongo;
	}
	
	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

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
