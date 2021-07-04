package app.java.agentapp.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.java.agentapp.model.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>{

	ShoppingCart findShoppingCartById(Long id);
	
	@Query("select s from ShoppingCart s where s.customer.username = ?1")
	Collection<ShoppingCart> findByCustomerUsername(String username);
}
