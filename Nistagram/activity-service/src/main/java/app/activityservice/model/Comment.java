package app.activityservice.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;

import app.activityservice.enums.PostType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {

	@Id
	@SequenceGenerator(name = "commentSeqGen", sequenceName = "commentSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commentSeqGen")
	@Setter(AccessLevel.NONE)
	private Long id;	
	
	@Column
	@CreationTimestamp
	private LocalDateTime timestamp;
		
	@Column	
	private String text;
	
	@Column	
	private long postId;	
	
	@Column		
	private PostType postType;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
	private Profile owner;	
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "comment_tagged_profiles", joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "id"))
	private Set<Profile> tagged = new HashSet<Profile>();
	
}
