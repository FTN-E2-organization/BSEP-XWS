package app.publishingservice.model;

import javax.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import app.publishingservice.enums.ContentType;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ReportContentRequest {

	@Id
	@SequenceGenerator(name = "reportContentSeqGen", sequenceName = "reportContentSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reportContentSeqGen")
	@Setter(AccessLevel.NONE)
	private Long id;
	
	@Column
	String reason;
	
	@Column
	@ColumnDefault("false")
	private boolean isApproved;
	
	@Column(nullable=false)
	private int contentId;
	
	@Column
	@Enumerated(EnumType.STRING)
	private ContentType contentType;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = true)
	private Profile profile;
	
	
}
