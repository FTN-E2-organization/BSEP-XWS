package app.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.authservice.model.AgentApiToken;

public interface AgentApiTokenRepository extends JpaRepository<AgentApiToken, Long>{

	@Query("select t.token from AgentApiToken t where t.username = ?1")
	String findTokenByUsername(String username);
}
