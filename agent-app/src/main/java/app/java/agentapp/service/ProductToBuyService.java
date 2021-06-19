package app.java.agentapp.service;

import java.util.Collection;

import app.java.agentapp.dto.ProductToBuyDTO;

public interface ProductToBuyService {

	void createProductToBuy(ProductToBuyDTO productToBuyDTO);
	void deleteProductToBuy(Long id);
	Collection<ProductToBuyDTO> findByShoppingCartId(Long id);
}
