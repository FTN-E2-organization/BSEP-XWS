package app.java.agentapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.java.agentapp.model.ProductToBuy;

public interface ProductToBuyRepository extends JpaRepository<ProductToBuy, Long>{

}
