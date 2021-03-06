package app.java.agentapp.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
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
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> createProduct(@RequestBody AddProductDTO productDTO) {
		try {
			Long productId = productService.save(productDTO);
			return new ResponseEntity<Long>(productId, HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while creating product.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/upload")
	public ModelAndView addProductImage(@FormParam("file") MultipartFile file, @QueryParam(value = "productId") Long productId) {		
		try {
			productService.upload(file, productId);

			return new ModelAndView("redirect:" + "http://localhost:8091/html/myProducts.html");
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:" + "http://localhost:8091/html/addProduct.html");
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
			ProductDTO productDTO = new ProductDTO(product.getId(), product.getPrice(), product.getAvailableQuantity(), product.getAgent().getId(), product.isDeleted(), product.getImagePath(), product.getName());	
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
	
	@GetMapping("/all")
	public ResponseEntity<?> findAllProducts() {

		try {
			Collection<ProductDTO> productDTOs = productService.findAllProducts();	
			return new ResponseEntity<Collection<ProductDTO>>(productDTOs, HttpStatus.OK);
		} catch (Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//@PreAuthorize("hasAuthority('campaignManagement')")
	@GetMapping("/agent/{username}")
	public ResponseEntity<?> findProductsByAgentUsername(@PathVariable String username)  {
		try {
			
			Collection<ProductDTO> productDTOs = productService.findProductsByAgentUsername(username);
			return new ResponseEntity<Collection<ProductDTO>>(productDTOs, HttpStatus.OK);
		} catch (Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/{id}/{quantity}")
	public ResponseEntity<?> updateTotalPrice(@PathVariable Long id, @PathVariable int quantity){
		try {
			productService.updateQuantity(id, quantity);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while updating available quantity.", HttpStatus.BAD_REQUEST);
		}
	}
	
}
