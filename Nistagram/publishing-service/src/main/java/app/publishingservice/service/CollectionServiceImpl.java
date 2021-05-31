package app.publishingservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.publishingservice.model.Collection;
import app.publishingservice.repository.CollectionRepository;
import app.publishingservice.repository.ProfileRepository;

@Service
public class CollectionServiceImpl implements CollectionService {

	private CollectionRepository collectionRepository;
	private ProfileRepository profileRepository;
	
	@Autowired
	public CollectionServiceImpl(CollectionRepository collectionRepository, ProfileRepository profileRepository) {
		this.collectionRepository = collectionRepository;
		this.profileRepository = profileRepository;
	}

	@Override
	public List<String> getAllByUsername(String username) {
		return collectionRepository.findAllByUsername(username);
	}

	@Override
	public void createIfDoesNotExist(String collectionName) {
		if(collectionRepository.findByName(collectionName) == null) {
			create(collectionName);
		}
	}	
	
	@Override
	public void create(String collectionName) {
		Collection collection = new Collection();
		collection.setName(collectionName);		
		collectionRepository.save(collection);
	}	
	
}
