package app.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.authservice.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	Admin findByUsername(String username);
	boolean existsByUsername(String usename);
	
	@Query(value = "select salt from admin a where a.username=?1", nativeQuery = true)
	String getSaltByUsername(String username);
}
