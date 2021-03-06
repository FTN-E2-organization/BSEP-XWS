package app.publishingservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.publishingservice.model.Hashtag;
import app.publishingservice.repository.HashtagRepository;

@Service
public class HashtagServiceImpl implements HashtagService {
	
	private HashtagRepository hashtagRepository;
	
	@Autowired
	public HashtagServiceImpl(HashtagRepository hashtagRepository) {
		this.hashtagRepository = hashtagRepository;
	}

	@Override
	public void create(String name) {
		Hashtag hashtag = new Hashtag();
		
		if(!name.startsWith("#"))
			name = "#" + name;
		hashtag.setName(name);
		
		hashtagRepository.save(hashtag);
	}

	@Override
	public boolean existsByName(String name) {
		return hashtagRepository.existsByName(name);
	}

	@Override
	public Hashtag findByName(String name) {
		if(!name.startsWith("#"))
			name = "#" + name;
		return hashtagRepository.findByName(name);
	}

	@Override
	public void createIfDoesNotExist(List<String> names) {
		for(String name:names) {
			if(findByName(name) == null) {
				create(name);
			}
		}
		
	}

	@Override
	public List<String> getAll() {
		return hashtagRepository.findAll().stream().map(Hashtag::getName).collect(Collectors.toList());
	}

}
