package app.mediaservice.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.mediaservice.enums.ContentType;
import app.mediaservice.model.Media;

@Repository
public interface MediaRepository extends MongoRepository<Media, String> {

	List<Media> getMediaByIdContentAndContentType(Long idContent,ContentType type);
	
}