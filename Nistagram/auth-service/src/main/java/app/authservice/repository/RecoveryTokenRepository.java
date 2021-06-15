package app.authservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.authservice.model.RecoveryToken;
import app.authservice.model.User;	

@Repository("recoveryTokenRepository")
public interface RecoveryTokenRepository extends CrudRepository<RecoveryToken, String>{

	RecoveryToken findByRecoveryToken(String recoveryToken);
	RecoveryToken findByUser(User user);
}