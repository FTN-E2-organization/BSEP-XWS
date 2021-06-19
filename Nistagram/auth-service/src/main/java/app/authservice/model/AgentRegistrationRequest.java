package app.authservice.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AgentRegistrationRequest {

	@Id
	@SequenceGenerator(name = "agentRegistrationSeqGen", sequenceName = "agentRegistrationSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agentRegistrationSeqGen")
	@Setter(AccessLevel.NONE)
	private Long id;
	
	@Column
	@ColumnDefault("false")
	private Boolean isApproved;
	
	@Column
	private String email;
	
	@Column
	private String webSite;
	
	@Column
	@CreationTimestamp
	private LocalDateTime timestamp;
	
	@Column
	@ColumnDefault("false")
	private Boolean isDeleted;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
	public Profile profile;
	
}
