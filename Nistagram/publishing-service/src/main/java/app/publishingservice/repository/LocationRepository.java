package app.publishingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.publishingservice.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

	boolean existsByName(String name);
	Location findByName(String name);
}
