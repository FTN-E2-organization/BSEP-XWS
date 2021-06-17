package app.java.agentapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.java.agentapp.dto.AddShoppingCartDTO;
import app.java.agentapp.service.ShoppingCartService;

@RestController
@RequestMapping(value = "api/shopping-cart")
public class ShoppingCartController {

	private ShoppingCartService shoppingCartService;
	
	@Autowired
	public ShoppingCartController(ShoppingCartService shoppingCartService) {
		this.shoppingCartService = shoppingCartService;
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> createShoppingCart(@RequestBody AddShoppingCartDTO shoppingCartDTO) {
		try {
			shoppingCartService.createShoppingCart(shoppingCartDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while creating shopping cart.", HttpStatus.BAD_REQUEST);
		}
	}
}
