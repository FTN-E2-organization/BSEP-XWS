package app.java.agentapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.java.agentapp.dto.ProductToBuyDTO;
import app.java.agentapp.service.ProductToBuyService;

@RestController
@RequestMapping(value = "api/product-buy")
public class ProductToBuyController {

	private ProductToBuyService productToBuyService;
	
	@Autowired
	public ProductToBuyController(ProductToBuyService productToBuyService) {
		this.productToBuyService = productToBuyService;
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> createShoppingCart(@RequestBody ProductToBuyDTO productToBuyDTO) {
		try {
			productToBuyService.createProductToBuy(productToBuyDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while creating product to buy.", HttpStatus.BAD_REQUEST);
		}
	}
}
