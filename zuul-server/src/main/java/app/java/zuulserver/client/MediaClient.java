package app.java.zuulserver.client;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import app.java.zuulserver.config.FeignSupportConfig;
import app.java.zuulserver.dto.MediaDTO;
import app.java.zuulserver.enums.ContentType;

@FeignClient(name = "media-service", configuration = FeignSupportConfig.class)
public interface MediaClient {
	
	@GetMapping("api/media/one/{idContent}/{type}")
	Collection<MediaDTO> getMediaById(@PathVariable Long idContent,@PathVariable ContentType type);
	
	@PostMapping(value = "api/media/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<String> fileUpload(@RequestPart(value = "file") MultipartFile file, @RequestPart(value = "uploadInfo") String uploadInfoDTO);

}
