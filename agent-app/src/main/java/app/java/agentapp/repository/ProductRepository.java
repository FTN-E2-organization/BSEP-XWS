package app.java.agentapp.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.java.agentapp.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	Product findProductById(Long id);
	
	@Query("select p from Product p where p.agent.id = ?1")
	Collection<Product> findByAgentId(Long id);
}
