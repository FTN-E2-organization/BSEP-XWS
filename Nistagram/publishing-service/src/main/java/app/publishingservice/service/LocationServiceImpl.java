package app.publishingservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.publishingservice.model.Location;
import app.publishingservice.repository.LocationRepository;

@Service
public class LocationServiceImpl implements LocationService {
	
	private LocationRepository locationRepository;
	
	@Autowired
	public LocationServiceImpl(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

	@Override
	public void create(String name) {
		Location location = new Location();
		location.setName(name);
		
		locationRepository.save(location);
	}

	@Override
	public boolean existsByName(String name) {
		return locationRepository.existsByName(name);
	}

	@Override
	public Location findByName(String name) {
		return locationRepository.findByName(name);
	}

	@Override
	public void createIfDoesNotExist(String name) {
		if(findByName(name) == null) {
			create(name);
		}
	}

	@Override
	public List<String> getAll() {
		return locationRepository.findAll().stream().map(Location::getName).collect(Collectors.toList());
	}

}
