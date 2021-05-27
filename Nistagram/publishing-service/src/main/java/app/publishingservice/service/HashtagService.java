package app.publishingservice.service;

import java.util.List;

import app.publishingservice.model.Hashtag;

public interface HashtagService {

	void create(String name);
	boolean existsByName(String name);
	Hashtag findByName(String name);
	void createIfDoesNotExist(List<String> names);
	List<String> getAll();
}
