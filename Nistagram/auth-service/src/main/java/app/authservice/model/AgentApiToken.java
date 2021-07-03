package app.authservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AgentApiToken {

	@Id
	@SequenceGenerator(name = "agentApiTokenSeqGen", sequenceName = "agentApiTokenSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agentApiTokenSeqGen")
	@Setter(AccessLevel.NONE)
	private Long id;
	
	@Column(nullable=false)
	private String username;
	
	@Column(nullable=false)
	@Size(max = 1500)
	private String token;
}
