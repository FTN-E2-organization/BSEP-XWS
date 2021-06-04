package app.java.zuulserver.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import app.java.zuulserver.dto.ProfileOverviewDTO;

@FeignClient(name = "auth-service")
public interface AuthClient {

	@GetMapping("api/auth/profile/{username}")
	ProfileOverviewDTO getProfile(@PathVariable("username") String username);
	
}
