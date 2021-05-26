package app.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.userservice.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
