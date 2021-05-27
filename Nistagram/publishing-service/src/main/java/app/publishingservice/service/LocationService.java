package app.publishingservice.service;

import app.publishingservice.model.Location;

public interface LocationService {

	void create(String name);
	boolean existsByName(String name);
	Location findByName(String name);
	void createIfDoesNotExist(String name);
}
