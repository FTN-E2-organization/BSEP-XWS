package app.activityservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import app.activityservice.enums.OwnerType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Click {

	@Id
	@SequenceGenerator(name = "clickSeqGen", sequenceName = "clickSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clickSeqGen")
	@Setter(AccessLevel.NONE)
	private Long id;	
	
	@Column	
	private OwnerType ownerType;
	
	@Column		
	private long campaignId;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	private Profile owner;	
	
}
