package app.mediaservice.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import app.mediaservice.model.Media;

@Repository
public interface MediaRepository extends MongoRepository<Media, String> {

	Media getMediaByIdContent(Long idContent);
	
}