package app.notificationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.notificationservice.service.NotificationService;
import app.notificationservice.dto.NotificationDTO;

@RestController
@RequestMapping(value = "api/notification/notification")
public class NotificationController {

	private NotificationService notificationService;
	
	@Autowired
	public NotificationController(NotificationService notificationService) {
		this.notificationService = notificationService;
	}
	
	@PostMapping()
	public ResponseEntity<?> create(@RequestBody NotificationDTO notificationDTO){
		try {	        
			notificationService.create(notificationDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("An error occurred while creating notification.", HttpStatus.BAD_REQUEST);
		}
	}	
	
	
}
