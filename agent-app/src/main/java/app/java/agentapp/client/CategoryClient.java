package app.java.agentapp.client;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import app.java.agentapp.dto.CategoryDTO;

@FeignClient(name = "auth-service", url = "${client.auth}")
public interface CategoryClient {

	@GetMapping("/profile/categories")
	public Collection<CategoryDTO> getAllCategories();
	
	@GetMapping("/agent-api-token/check/{token}")
    public boolean checkToken(@PathVariable String token);
}
