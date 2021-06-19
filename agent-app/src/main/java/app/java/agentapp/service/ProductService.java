package app.java.agentapp.service;

import java.util.Collection;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import app.java.agentapp.dto.ProductDTO;
import app.java.agentapp.model.Product;

public interface ProductService {

	Product findProductById(Long id);
	void init();
	public void deleteAll();
	void save(MultipartFile file, double price, int availableQuantity, Long agentId, String name);
	Resource load(String filename);
	void update(ProductDTO productDTO);
	void delete(Long id);
	Collection<ProductDTO> findAllProducts();
	Collection<ProductDTO> findProductsByAgentId(Long id);
	void updateQuantity(Long id, int quantity);
}
