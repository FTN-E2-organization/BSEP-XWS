package app.java.agentapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.java.agentapp.dto.AddProductDTO;
import app.java.agentapp.dto.ProductDTO;
import app.java.agentapp.model.Product;
import app.java.agentapp.service.ProductService;

@RestController
@RequestMapping(value = "api/product")
public class ProductController {
	
	private ProductService productService;
	
	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@PostMapping("/upload")
	public ResponseEntity<String> addProduct(@RequestParam("file") MultipartFile file, @RequestParam("productInfo") String productInfo) {		
		try {
			AddProductDTO productDTO = new ObjectMapper().readValue(productInfo, AddProductDTO.class);
			productService.save(file, productDTO.price, productDTO.availableQuantity, productDTO.agentId);

			return ResponseEntity.status(HttpStatus.OK).body(new String("Uploaded product successfully."));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new String( "Could not upload the product."));
		}
	}
	
	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = productService.load(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findProductById(@PathVariable Long id) {

		try {
			Product product = productService.findProductById(id);
			ProductDTO productDTO = new ProductDTO(product.getId(), product.getPrice(), product.getAvailableQuantity(), product.getAgent().getId(), product.isDeleted(), product.getImagePath());			
			return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
		} catch (Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(consumes = "application/json")
	public ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO){
		try {
			productService.update(productDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while updating product's information.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/{id}/delete")
	public ResponseEntity<?> deleteProduct(@PathVariable Long id){
		try {
			productService.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while deleting product.", HttpStatus.BAD_REQUEST);
		}
	}
}
