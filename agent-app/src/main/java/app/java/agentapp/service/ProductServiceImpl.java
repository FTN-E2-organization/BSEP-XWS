package app.java.agentapp.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import app.java.agentapp.dto.ProductDTO;
import app.java.agentapp.model.Agent;
import app.java.agentapp.model.Product;
import app.java.agentapp.repository.AgentRepository;
import app.java.agentapp.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

	private ProductRepository productRepository;
	private AgentRepository agentRepository;
	private final Path root = Paths.get("uploads");
	
	@Autowired
	public ProductServiceImpl(ProductRepository productRepository, AgentRepository agentRepository) {
		this.productRepository = productRepository;
		this.agentRepository = agentRepository;
	}

	@Override
	public void init() {
		try {
			Files.createDirectory(root);
		} catch (IOException e) {
			// throw new RuntimeException("Could not initialize folder for upload!");
		}
		
	}

	@Override
	public void save(MultipartFile file, double price, int availableQuantity, Long agentId) {
		try {
			UUID uuid = UUID.randomUUID();
			Path path = this.root.resolve(uuid.toString() + "_" + file.getOriginalFilename());
			Files.copy(file.getInputStream(), path);
			Product product = new Product();
			product.setPrice(price);
			product.setAvailableQuantity(availableQuantity);
			product.setDeleted(false);
			Agent agent = agentRepository.findAgentById(agentId);
			product.setAgent(agent);
			product.setImagePath(path.getFileName().toString());
			productRepository.save(product);
			
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(root.toFile());
	}

	@Override
	public Resource load(String filename) {
		try {
			Path file = root.resolve(filename);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	@Override
	public Product findProductById(Long id) {
		return productRepository.findProductById(id);
	}

	@Override
	public void update(ProductDTO productDTO) {
		Product product = productRepository.findProductById(productDTO.id);
		Agent agent = agentRepository.findAgentById(productDTO.agentId);
		
		product.setPrice(productDTO.price);
		product.setAvailableQuantity(productDTO.availableQuantity);
		product.setAgent(agent);
		
		productRepository.save(product);
		
	}

	@Override
	public void delete(Long id) {
		Product product = productRepository.findProductById(id);
		product.setDeleted(true);
		
		productRepository.save(product);
		
	}
}
