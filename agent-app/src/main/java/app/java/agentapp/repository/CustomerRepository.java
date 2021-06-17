package app.java.agentapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.java.agentapp.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

	Customer findByUsername(String username);
	boolean existsByUsername(String username);
	
}
