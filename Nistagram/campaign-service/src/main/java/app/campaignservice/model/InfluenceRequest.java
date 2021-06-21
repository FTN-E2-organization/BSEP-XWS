package app.campaignservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class InfluenceRequest {

	@Id
	@SequenceGenerator(name = "influenceRequestSeqGen", sequenceName = "influenceRequestSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "influenceRequestSeqGen")
	@Setter(AccessLevel.NONE)
	private Long id;	
	
	@Column
	private boolean isApproved;		
	
	
	
}
