package app.publishingservice.model;

import java.util.*;
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
	private ContentType contentType;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "reported_request_profiles", joinColumns = @JoinColumn(name = "request_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "id"))
	Set<Profile> profiles = new HashSet<Profile>();
	
}
