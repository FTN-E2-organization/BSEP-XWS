package app.campaignservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ad {

	@Id
	@SequenceGenerator(name = "adSeqGen", sequenceName = "adSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adSeqGen")
	@Setter(AccessLevel.NONE)
	private Long id;
	
	@Column(nullable = false)
	private String contentPath;	
	
	@Column(nullable = false)
	private String productLink;	
	
	@ManyToOne
	private AdCategory adCategory;
	
}
