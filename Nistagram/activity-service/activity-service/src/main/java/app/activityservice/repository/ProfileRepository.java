package app.activityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.activityservice.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long>{

	
}
