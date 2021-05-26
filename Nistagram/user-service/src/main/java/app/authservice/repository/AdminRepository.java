package app.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.userservice.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

}
