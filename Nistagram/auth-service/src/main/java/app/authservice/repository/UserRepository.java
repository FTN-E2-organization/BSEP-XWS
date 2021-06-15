package app.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.authservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
