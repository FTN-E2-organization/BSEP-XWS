package app.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.authservice.model.ConfirmationToken;
import app.authservice.model.Profile;

@Repository("confirmationTokenRepository")
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, String> {

	ConfirmationToken findByConfirmationToken(String confirmationToken);
	
	ConfirmationToken findByProfile(Profile profile);	
	
	@Query(value = "select * from profile p, confirmation_token ct where ct.profile_id = p.id and p.username=?1 limit 1", nativeQuery = true)
	ConfirmationToken getTokenByUsername(String username);	
	
}
