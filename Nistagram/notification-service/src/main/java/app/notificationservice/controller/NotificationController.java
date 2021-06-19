package app.notificationservice.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.notificationservice.service.NotificationService;
import app.notificationservice.dto.NotificationDTO;

@RestController
@RequestMapping(value = "api/notification")
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
			return new ResponseEntity<String>("An error occurred while creating notification. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/all/profile/{username}")
	public ResponseEntity<?> findAllNotificationsByProfileUsername(@PathVariable String username){
		
		try {
			Collection<NotificationDTO> notifications = notificationService.getNotificationsByProfileUsername(username);
			return new ResponseEntity<Collection<NotificationDTO>>(notifications, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}	
	
	
}
