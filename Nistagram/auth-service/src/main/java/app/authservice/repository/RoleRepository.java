package app.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.authservice.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);
}
