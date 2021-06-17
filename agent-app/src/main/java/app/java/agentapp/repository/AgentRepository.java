package app.java.agentapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.java.agentapp.model.Agent;

public interface AgentRepository extends JpaRepository<Agent, Long>{

	Agent findByUsername(String username);
	boolean existsByUsername(String username);
}
