package app.publishingservice.service;

public interface LocationService {

	void create(String name);
	boolean existsByName(String name);
}
