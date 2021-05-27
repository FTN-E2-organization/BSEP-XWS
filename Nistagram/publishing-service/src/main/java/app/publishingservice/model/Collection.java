package app.publishingservice.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Collection {

	@Id
	@SequenceGenerator(name = "collectionSeqGen", sequenceName = "collectionSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "collectionSeqGen")
	@Setter(AccessLevel.NONE)
	private Long id;	
	
	@Column
	private String name;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "collection_of_favorite_posts", joinColumns = @JoinColumn(name = "collection_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "favorite_post_id", referencedColumnName = "id"))
	public Set<FavouritePost> favouritePosts;
	
	
}
