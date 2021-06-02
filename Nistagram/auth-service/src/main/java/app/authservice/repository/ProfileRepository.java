package app.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.authservice.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

	Profile findByUsername(String username);
}
