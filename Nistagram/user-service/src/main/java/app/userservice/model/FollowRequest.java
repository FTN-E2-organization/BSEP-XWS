package app.userservice.model;

import java.time.LocalDateTime;
import lombok.*;
import javax.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FollowRequest {

	@Id
	@SequenceGenerator(name = "followRequestSeqGen", sequenceName = "followRequestSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "followRequestSeqGen")
	private Long id;
	
	@Column
	@CreationTimestamp
	private LocalDateTime timestamp;
	
	@Column
	@ColumnDefault("false")
	private boolean isApproved;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	private Profile base;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	private Profile follower;
}
