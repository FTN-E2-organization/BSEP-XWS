package app.publishingservice.service;

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
		hashtag.setName(name);
		
		hashtagRepository.save(hashtag);
	}

	@Override
	public boolean existsByName(String name) {
		return hashtagRepository.existsByName(name);
	}

}
