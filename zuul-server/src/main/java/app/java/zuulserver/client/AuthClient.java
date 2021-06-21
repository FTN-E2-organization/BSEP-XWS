package app.java.zuulserver.client;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import app.java.zuulserver.dto.ProfileDTO;
import app.java.zuulserver.dto.ProfileOverviewDTO;
import app.java.zuulserver.dto.VerificationResponseDTO;

@FeignClient(name = "auth-service")
public interface AuthClient {

	@GetMapping("api/auth/profile/{username}")
	ProfileOverviewDTO getProfile(@PathVariable("username") String username);
	
	@PostMapping("api/auth/verify")
    VerificationResponseDTO verify(String token);
	
	@GetMapping("api/auth/profile/search/{typeOfSearch}")
	Collection<ProfileDTO> getProfiles(@PathVariable String typeOfSearch);

}
