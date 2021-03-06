package app.java.agentapp.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import app.java.agentapp.dto.AddProductDTO;
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
	public void upload(MultipartFile file, Long productId) {
		try {
			UUID uuid = UUID.randomUUID();
			Path path = this.root.resolve(uuid.toString() + "_" + file.getOriginalFilename());
			Files.copy(file.getInputStream(), path);
			Product product = productRepository.findProductById(productId);
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
		
		product.setPrice(productDTO.price);
		product.setAvailableQuantity(productDTO.availableQuantity);
		
		productRepository.save(product);
		
	}

	@Override
	public void delete(Long id) {
		Product product = productRepository.findProductById(id);
		product.setDeleted(true);
		
		productRepository.save(product);
		
	}

	@Override
	public Collection<ProductDTO> findAllProducts() {
		Collection<Product> products = productRepository.findAll();
		Collection<ProductDTO> productDTOs = new ArrayList<>();
		for(Product p : products) {
			if(p.isDeleted()==false) {
			productDTOs.add(new ProductDTO(p.getId(), p.getPrice(), p.getAvailableQuantity(), p.getAgent().getId(), p.isDeleted(), p.getImagePath(), p.getName()));
			}
		}
		return productDTOs;
	}

	@Override
	public Collection<ProductDTO>findProductsByAgentUsername(String username) {
		Collection<Product> products = productRepository.findByAgentUsername(username);
		Collection<ProductDTO> productDTOs = new ArrayList<>();
		for(Product p : products) {
			if(p.isDeleted()==false) {
			productDTOs.add(new ProductDTO(p.getId(), p.getPrice(), p.getAvailableQuantity(), p.getAgent().getId(), p.isDeleted(), p.getImagePath(), p.getName()));
			}
		}
		return productDTOs;
	}

	@Override
	public void updateQuantity(Long id, int quantity) {
		Product product = productRepository.findProductById(id);
		product.setAvailableQuantity(product.getAvailableQuantity() - quantity);
		
		productRepository.save(product);
	}

	@Override
	public Long save(AddProductDTO productDTO) {
		Product product = new Product();
		Agent agent = agentRepository.findByUsername(productDTO.agentUsername);
		product.setAgent(agent);
		product.setAvailableQuantity(productDTO.availableQuantity);
		product.setDeleted(false);
		product.setName(productDTO.name);
		product.setPrice(productDTO.price);
		product.setImagePath("");
		
		Product savedProduct = productRepository.save(product);
		return savedProduct.getId();
	}
}
