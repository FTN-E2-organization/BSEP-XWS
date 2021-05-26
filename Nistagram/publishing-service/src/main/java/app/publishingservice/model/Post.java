package app.publishingservice.model;

import java.time.LocalDateTime;
import javax.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {

	@Id
	@SequenceGenerator(name = "postSeqGen", sequenceName = "postSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postSeqGen")
	@Setter(AccessLevel.NONE)
	private Long id;
	
	@Column
	@CreationTimestamp
	private LocalDateTime timestamp;
	
	@Column
	@ColumnDefault("false")
	private boolean isDeleted;
	
	@Column
	private String description;
	
}
