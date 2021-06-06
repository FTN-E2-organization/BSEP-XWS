package app.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.authservice.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	Admin findByUsername(String username);
}
