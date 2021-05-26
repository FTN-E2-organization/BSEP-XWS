package app.publishingservice.service;

public interface HashtagService {

	void create(String name);
	boolean existsByName(String name);
	
}
