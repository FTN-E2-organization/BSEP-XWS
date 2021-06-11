package app.publishingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.publishingservice.service.LocationService;

@RestController
@RequestMapping(value = "api/publishing/location")
public class LocationController {

	private LocationService locationService;
	
	@Autowired
	public LocationController(LocationService locationService) {
		this.locationService = locationService;
	}
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		try {
			return new ResponseEntity<>(locationService.getAll(), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting locations.", HttpStatus.BAD_REQUEST);
		}
	}
}
