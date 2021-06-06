package app.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.authservice.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

	Authority findByName(String name);
}
