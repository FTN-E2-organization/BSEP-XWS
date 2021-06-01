package app.publishingservice.service;

import java.util.List;

public interface CollectionService {

	List<String> getAllByUsername(String username);

	void createIfDoesNotExist(String collectionName);
	void create(String collectionName);

	
}
