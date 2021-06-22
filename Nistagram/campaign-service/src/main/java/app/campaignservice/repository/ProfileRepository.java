package app.campaignservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.campaignservice.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
