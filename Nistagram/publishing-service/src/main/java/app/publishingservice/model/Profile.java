package app.publishingservice.model;

import javax.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Profile {

	@Id
	@SequenceGenerator(name = "profileSeqGen", sequenceName = "profileSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profileSeqGen")
	@Setter(AccessLevel.NONE)
	private Long id;
	
	@Column(unique=true, nullable=false)
	private String username;
	
	@Column
	private boolean isPublic;
	
	@Column
	@ColumnDefault("false")
	private boolean isDeleted;
	
	@Column
	private boolean allowedTagging;

}
