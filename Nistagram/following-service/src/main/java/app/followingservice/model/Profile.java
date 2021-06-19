package app.followingservice.model;

import java.util.Set;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import lombok.Data;

@Data
@NodeEntity
public class Profile {
	
	@Id
    @GeneratedValue
	private Long id;
	
	private String username;
	
	private boolean isPublic;
	
	private boolean isBlocked;

	@Relationship(type = "FOLLOW")
    private Set<Friendship> friendships;
	
	@Relationship(type = "REQUEST")
    private Set<FollowRequest> requests;
	
	@Relationship(type = "BLOCK")
    private Set<Blocking> blocks;
	
	@Relationship(type = "INTERESTED", direction = Relationship.INCOMING)
    private Set<ProfileCategory> categories;

	public Profile() {

	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public Set<Friendship> getFriendships() {
		return friendships;
	}

	public void setFriendships(Set<Friendship> friendships) {
		this.friendships = friendships;
	}

	public Set<ProfileCategory> getCategories() {
		return categories;
	}

	public void setCategories(Set<ProfileCategory> categories) {
		this.categories = categories;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<FollowRequest> getRequests() {
		return requests;
	}

	public void setRequests(Set<FollowRequest> requests) {
		this.requests = requests;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
}
