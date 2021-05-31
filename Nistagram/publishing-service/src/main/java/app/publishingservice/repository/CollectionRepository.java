package app.publishingservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.publishingservice.model.Collection;
import app.publishingservice.model.FavouritePost;

public interface CollectionRepository extends JpaRepository<Collection, Long> {

	boolean existsByName(String name);
	
	Collection findByName(String name);
	
	@Query(value = "select distinct c.name from collection c, favourite_post fp, profile " +
					" where c.id=fp.collection_id and profile.id=fp.owner_id" +
					" and profile.username=?1 ", nativeQuery = true)
	List<String> findAllByUsername(String username);

}
