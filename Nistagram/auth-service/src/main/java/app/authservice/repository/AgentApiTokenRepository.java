package app.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.authservice.model.AgentApiToken;

public interface AgentApiTokenRepository extends JpaRepository<AgentApiToken, Long>{

}
