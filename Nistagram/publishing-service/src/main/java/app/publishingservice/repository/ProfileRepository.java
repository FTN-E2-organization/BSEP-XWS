package app.publishingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.publishingservice.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

	boolean existsByUsername(String username);
	Profile findByUsername(String username);
}
