package app.java.agentapp.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import app.java.agentapp.dto.ProductToBuyDTO;
import app.java.agentapp.dto.ShoppingCartDTO;
import app.java.agentapp.model.Product;
import app.java.agentapp.model.ProductToBuy;
import app.java.agentapp.model.ShoppingCart;
import app.java.agentapp.repository.ProductRepository;
import app.java.agentapp.repository.ProductToBuyRepository;
import app.java.agentapp.repository.ShoppingCartRepository;

@Service
public class ProductToBuyServiceImpl implements ProductToBuyService{

	private ProductToBuyRepository productToBuyRepository;
	private ProductRepository productRepository;
	private ShoppingCartRepository shoppingCartRepository;
	
	@Autowired
	public ProductToBuyServiceImpl(ProductToBuyRepository productToBuyRepository, ProductRepository productRepository, ShoppingCartRepository shoppingCartRepository) {
		this.productToBuyRepository = productToBuyRepository;
		this.productRepository = productRepository;
		this.shoppingCartRepository = shoppingCartRepository;
	}

	@Override
	public void createProductToBuy(ProductToBuyDTO productToBuyDTO) {
		ProductToBuy productToBuy = new ProductToBuy();
		productToBuy.setQuantity(productToBuyDTO.quantity);
		
		Product product = productRepository.findProductById(productToBuyDTO.productId);
		ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartById(productToBuyDTO.shoppingCartId);
		
		productToBuy.setProduct(product);
		productToBuy.setShoppingCart(shoppingCart);
		productToBuy.setDeleted(false);
		
		productToBuyRepository.save(productToBuy);
	}

	@Override
	public void deleteProductToBuy(Long id) {
		ProductToBuy productToBuy = productToBuyRepository.findProductToBuyById(id);
		productToBuy.setDeleted(true);
		
		productToBuyRepository.save(productToBuy);
	}

	@Override
	public Collection<ProductToBuyDTO> findByShoppingCartId(Long id) {
		Collection<ProductToBuy> products = productToBuyRepository.findByShoppingCartId(id);
		Collection<ProductToBuyDTO> productDTOs = new ArrayList<>();
		for(ProductToBuy p : products) {
			if(p.isDeleted()==false) {
				productDTOs.add(new ProductToBuyDTO(p.getId(), p.getQuantity(), p.getProduct().getId(), p.getShoppingCart().getId()));
			}
			}
		return productDTOs;
	}
	
}
