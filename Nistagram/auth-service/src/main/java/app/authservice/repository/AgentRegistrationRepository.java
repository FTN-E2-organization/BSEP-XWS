package app.authservice.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.authservice.model.AgentRegistrationRequest;

public interface AgentRegistrationRepository extends JpaRepository<AgentRegistrationRequest, Long> {

	@Query(value = "select * from agent_registration_request r, profile p where r.profile_id = p.id and "
			+ "r.is_approved = false and p.is_blocked = false and r.is_deleted=false", nativeQuery = true)
	Collection<AgentRegistrationRequest> findAllDisapproved();
	
	@Query(value = "select * from agent_registration_request r, profile p where r.profile_id = p.id and p.username = ?1 and r.is_deleted=false", nativeQuery = true)
	Collection<AgentRegistrationRequest> findAllByUsername(String username);
}
