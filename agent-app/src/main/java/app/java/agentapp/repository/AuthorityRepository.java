package app.java.agentapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.java.agentapp.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

	Authority findByName(String name);
}
