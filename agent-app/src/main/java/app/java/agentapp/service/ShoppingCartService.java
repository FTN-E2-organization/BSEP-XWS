package app.java.agentapp.service;

import java.util.Collection;

import app.java.agentapp.dto.AddShoppingCartDTO;
import app.java.agentapp.dto.ShoppingCartDTO;

public interface ShoppingCartService {

	void createShoppingCart(AddShoppingCartDTO shoppingCartDTO);
	void updateTotalPrice(Long id, double price);
	void deleteShopingCart(Long id);
	void finishShoppingCart(Long id);
	Collection<ShoppingCartDTO> findByCustomerId(Long id);
}
