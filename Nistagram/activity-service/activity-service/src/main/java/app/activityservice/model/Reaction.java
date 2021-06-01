package app.activityservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import app.activityservice.enums.PostType;
import app.activityservice.enums.ReactionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reaction {

	@Id
	@SequenceGenerator(name = "commentSeqGen", sequenceName = "commentSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commentSeqGen")
	@Setter(AccessLevel.NONE)
	private Long id;	
	
	@Column
	private ReactionType reactionType;
	
	@Column
	private long postId;
	
	@Column		
	private PostType postType;	
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
	private Profile owner;	
}
