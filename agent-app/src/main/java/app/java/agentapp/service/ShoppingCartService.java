package app.java.agentapp.service;

import app.java.agentapp.dto.AddShoppingCartDTO;

public interface ShoppingCartService {

	void createShoppingCart(AddShoppingCartDTO shoppingCartDTO);
	void updateTotalPrice(Long id, double price);
	void deleteShopingCart(Long id);
}
