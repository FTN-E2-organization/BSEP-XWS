package app.publishingservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.publishingservice.model.Collection;
import app.publishingservice.repository.CollectionRepository;

@Service
public class CollectionServiceImpl implements CollectionService {

	private CollectionRepository collectionRepository;
	
	@Autowired
	public CollectionServiceImpl(CollectionRepository collectionRepository) {
		this.collectionRepository = collectionRepository;
	}

	@Override
	public List<String> getAll() {
		return collectionRepository.findAll().stream().map(Collection::getName).collect(Collectors.toList());
	}	
	
}
