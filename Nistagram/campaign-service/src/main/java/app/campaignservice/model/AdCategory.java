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
public class AdCategory {

	@Id
	@SequenceGenerator(name = "adCategorySeqGen", sequenceName = "adCategorySeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adCategorySeqGen")
	@Setter(AccessLevel.NONE)
	private Long id;	
	
	@Column(nullable = false)
	private String name;	
	
	
	
	
}