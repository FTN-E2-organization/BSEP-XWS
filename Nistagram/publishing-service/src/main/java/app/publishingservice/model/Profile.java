package app.publishingservice.model;

import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Profile {

	@Id
	private Long id;
	
	@Column(unique=true, nullable=false)
	private String username;
	
	@Column
	protected boolean isPublic;
	
	@Column
	protected boolean isDeleted;
	
	@Column
	protected boolean allowedTagging;
	
}
