package app.publishingservice.model;

import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import org.hibernate.annotations.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Story {

	@Id
	@SequenceGenerator(name = "storySeqGen", sequenceName = "storySeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "storySeqGen")
	@Setter(AccessLevel.NONE)
	private Long id;
	
	@Column
	@CreationTimestamp
	private LocalDateTime timestamp;
	
	@Column
	String description;
	
	@Column 
	@ColumnDefault("false")
	private boolean isHighlight;
	
	@Column
	@ColumnDefault("false")
	private boolean isDeleted;
	
	@Column
	@ColumnDefault("false")
	private boolean forCloseFriends;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	private Profile owner;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
	private Location location;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "story_hashtags", joinColumns = @JoinColumn(name = "story_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "hashtag_id", referencedColumnName = "id"))
	private Set<Hashtag> hashtags = new HashSet<Hashtag>();
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "story_tagged_profiles", joinColumns = @JoinColumn(name = "story_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "id"))
	private Set<Profile> tagged = new HashSet<Profile>();
}
