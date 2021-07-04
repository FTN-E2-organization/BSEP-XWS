package app.notificationservice.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import app.notificationservice.dto.MessageDTO;
import app.notificationservice.service.MessageService;

@RestController
@RequestMapping(value = "api/notification/message")
public class MessageController {

	private MessageService messageService;
	
	@Autowired
	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}
	
	@PostMapping
	public ResponseEntity<?> sendTextMessage(@RequestBody MessageDTO messageDTO){
		try {
			return new ResponseEntity<MessageDTO>(messageService.sendTextMessage(messageDTO), HttpStatus.CREATED);
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<String>("An error occurred while sending a message.", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/chat/{firstUsername}/{secondUsername}")
	public ResponseEntity<?> getReceivedMessages(@PathVariable String firstUsername, @PathVariable String secondUsername){
		try {
			Collection<MessageDTO> messageDTOs = messageService.getChatBetweenTwoProfiles(firstUsername, secondUsername);
			return new ResponseEntity<Collection<MessageDTO>>(messageDTOs, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/chat-usernames/{username}")
	public ResponseEntity<?> getUsernamesFromChat(@PathVariable String username){
		try {
			Collection<String> usernames = messageService.getUsernamesFromChat(username);
			return new ResponseEntity<Collection<String>>(usernames, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/requests/{username}")
	public ResponseEntity<?> getMessageRequestsByUsername(@PathVariable String username){
		try {
			Collection<MessageDTO> messageDTOs = messageService.getMessageRequestsByUsername(username);
			return new ResponseEntity<Collection<MessageDTO>>(messageDTOs, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/approve/{id}")
	public ResponseEntity<?> approveMessageRequest(@PathVariable String id){
		try {
			messageService.approveMessageRequest(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>("An error occurred while approving a message request.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/reject/{id}")
	public ResponseEntity<?> rejectMessageRequest(@PathVariable String id){
		try {
			messageService.rejectMessageRequest(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>("An error occurred while rejecting a message request.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/delete/{id}")
	public ResponseEntity<?> deleteMessageRequest(@PathVariable String id){
		try {
			messageService.deleteMessageRequest(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>("An error occurred while deleting a message request.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/content-seen/{id}")
	public ResponseEntity<?> setOneTimeContentSeen(@PathVariable String id){
		try {
			messageService.setOneTimeContentSeen(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("An error occurred while setting one time content to seen.", HttpStatus.BAD_REQUEST);
		}
	}
	
}
