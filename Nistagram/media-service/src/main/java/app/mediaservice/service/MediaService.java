package app.mediaservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import org.springframework.core.io.Resource;

import app.mediaservice.dto.MediaDTO;
import app.mediaservice.enums.ContentType;
import app.mediaservice.model.Media;

public interface MediaService {
	
	List<MediaDTO> getMediaByIdContentAndType(Long idContent, ContentType type);
	
	void init();

	void save(MultipartFile file, Long idContent, ContentType type);

	Resource load(String filename);
	
	void deleteAll();
	
	void deleteOneByIdContentAndType(Long idContent, ContentType type);

}
