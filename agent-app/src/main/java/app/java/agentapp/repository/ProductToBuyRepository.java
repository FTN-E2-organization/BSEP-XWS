package app.java.agentapp.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.java.agentapp.model.ProductToBuy;

public interface ProductToBuyRepository extends JpaRepository<ProductToBuy, Long>{

	ProductToBuy findProductToBuyById(Long id);
	
	@Query("select p from ProductToBuy p where p.shoppingCart.id = ?1")
	Collection<ProductToBuy> findByShoppingCartId(Long id);
}
