package app.java.zuulserver.client;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import app.java.zuulserver.dto.MediaDTO;
import app.java.zuulserver.enums.ContentType;

@FeignClient(name = "media-service")
public interface MediaClient {
	
	@GetMapping("api/media/one/{idContent}/{type}")
	Collection<MediaDTO> getMediaById(@PathVariable Long idContent,@PathVariable ContentType type);
}
