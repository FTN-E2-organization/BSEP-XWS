package app.publishingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.publishingservice.model.Story;

public interface StoryRepository extends JpaRepository<Story, Long> {

}
