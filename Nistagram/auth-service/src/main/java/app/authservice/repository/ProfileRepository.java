package app.authservice.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.authservice.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

	Profile findByUsername(String username);
	boolean existsByUsername(String username);
	
	@Query(value="select * from profile where allowed_tagging=true", nativeQuery = true)
	List<Profile> findAllowTaggingProfiles();

	@Query(value="select * from profile where is_public=true", nativeQuery = true)
	Collection<Profile> findAllPublic();
	
}
