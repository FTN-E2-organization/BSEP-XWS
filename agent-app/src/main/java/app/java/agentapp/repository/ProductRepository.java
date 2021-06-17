package app.java.agentapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.java.agentapp.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	Product findProductById(Long id);
}
