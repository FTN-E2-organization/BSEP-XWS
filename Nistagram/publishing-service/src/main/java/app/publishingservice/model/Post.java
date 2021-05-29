package app.publishingservice.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
	private Profile owner;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
	private Location location;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "post_hashtags", joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "hashtag_id", referencedColumnName = "id"))
	private Set<Hashtag> hashtags = new HashSet<Hashtag>();
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "post_tagged_profiles", joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "id"))
	private Set<Profile> tagged = new HashSet<Profile>();
	
	
	
}
