package app.publishingservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.publishingservice.model.Collection;

public interface CollectionRepository extends JpaRepository<Collection, Long> {

	@Query(value = "select c.name from collection c, favourite_post_collections cfp, favourite_post fp, profile " +
					" where c.id=cfp.collections_id and fp.id=cfp.favourite_posts_id and profile.id=fp.owner_id" +
					" and profile.username=?1 ", nativeQuery = true)
	List<String> findAllByUsername(String username);

}
