package app.activityservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.ColumnDefault;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	@ColumnDefault("false")
	private boolean isDeleted;
	
	@Column
	private boolean allowedTagging;
	
}
