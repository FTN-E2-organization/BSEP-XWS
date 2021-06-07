package app.publishingservice.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.publishingservice.model.FavouritePost;

public interface FavouritePostRepository extends JpaRepository<FavouritePost, Long> {

	@Query(value = "select * from favourite_post fp, profile, post p " +
			" where profile.id=fp.owner_id and p.is_deleted=false " +
			" and profile.username=?1 and fp.post_id=p.id ", nativeQuery = true)	
	Collection<FavouritePost> getAllByUsername(String username);

	@Query(value = "select * from favourite_post fp, profile, post p, collection c " +
			" where profile.id=fp.owner_id and p.is_deleted=false and c.name=?2 " +
			" and profile.username=?1 and fp.collection_id=c.id and fp.post_id=p.id ", nativeQuery = true)		
	Collection<FavouritePost> getAllByUsernameAndCollection(String username, String collectionName);

	@Query(value = "select * from favourite_post fp, profile, post p, collection c " +
			" where profile.id=fp.owner_id and p.is_deleted=false and c.name=?1 and c.id=fp.collection_id " +
			" and profile.username=?3 and fp.post_id=p.id and p.id=?2 limit 1", nativeQuery = true)		
	FavouritePost getByCollectionNameAndPostId(String collectionName, long postId, String ownerUsername);

}
