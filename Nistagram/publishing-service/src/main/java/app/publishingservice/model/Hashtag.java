package app.publishingservice.model;

import javax.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Hashtag {

	@Id
	@SequenceGenerator(name = "hashtagSeqGen", sequenceName = "hashtagSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hashtagSeqGen")
	@Setter(AccessLevel.NONE)
	private Long id;
	
	@Column(nullable=false, unique = true)
	private String name;
	
}
