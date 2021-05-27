package app.publishingservice.service;

import java.util.List;

import app.publishingservice.model.Location;

public interface LocationService {

	void create(String name);
	boolean existsByName(String name);
	Location findByName(String name);
	void createIfDoesNotExist(String name);
	List<String> getAll();
}
