package app.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.authservice.model.ProfileVerification;


public interface VerificationRequestRepository extends JpaRepository<ProfileVerification, Long> {

	ProfileVerification findByProfileUsername(String username);

}
