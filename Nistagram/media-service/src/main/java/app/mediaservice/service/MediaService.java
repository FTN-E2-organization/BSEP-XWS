package app.mediaservice.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import app.mediaservice.enums.ContentType;
import app.mediaservice.model.Media;

public interface MediaService {
	
	Media getMediaByIdContent(Long idContent);
	
	void init();

	void save(MultipartFile file, Long idContent, ContentType type);

	Resource load(String filename);
	
	void deleteAll();
	
	void deleteOneByIdContent(Long idContent);

}
