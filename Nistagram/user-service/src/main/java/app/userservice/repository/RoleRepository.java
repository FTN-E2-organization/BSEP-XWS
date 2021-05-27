package app.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.userservice.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);
}
