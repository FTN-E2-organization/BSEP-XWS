package app.java.agentapp.service;

import java.util.Collection;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import app.java.agentapp.dto.AddProductDTO;
import app.java.agentapp.dto.ProductDTO;
import app.java.agentapp.model.Product;

public interface ProductService {

	Product findProductById(Long id);
	void init();
	public void deleteAll();
	void upload(MultipartFile file, Long productId);
	Long save(AddProductDTO productDTO);
	Resource load(String filename);
	void update(ProductDTO productDTO);
	void delete(Long id);
	Collection<ProductDTO> findAllProducts();
	Collection<ProductDTO> findProductsByAgentUsername(String username);
	void updateQuantity(Long id, int quantity);
}
