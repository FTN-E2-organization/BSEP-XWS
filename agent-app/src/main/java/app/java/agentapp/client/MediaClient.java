package app.java.agentapp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import app.java.agentapp.config.FeignSupportConfig;


@FeignClient(name = "media-service", url = "${client.media}")
public interface MediaClient {
	
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<String> fileUpload(@RequestPart(value = "file") MultipartFile file, @RequestPart(value = "uploadInfo") String uploadInfoDTO);

}