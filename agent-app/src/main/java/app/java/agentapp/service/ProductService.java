package app.java.agentapp.service;

import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

	void init();
	public void deleteAll();
	void save(MultipartFile file, double price, int availableQuantity, Long agentId);
}
