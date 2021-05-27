package app.publishingservice.model;

import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Location {

	@Id
	@SequenceGenerator(name = "locationSeqGen", sequenceName = "locationSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locationSeqGen")
	@Setter(AccessLevel.NONE)
	private Long id;
	
	@Column(nullable=false, unique = true)
	private String name;

}
