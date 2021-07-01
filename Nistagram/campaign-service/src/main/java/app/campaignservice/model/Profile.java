package app.campaignservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profile")
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
	private boolean isInfluencer;
	
	@Column
	private boolean isPublic;
	
	@Column
	@ColumnDefault("false")
	private boolean isBlocked;
	
}
