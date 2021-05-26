package app.publishingservice.service;

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

}
