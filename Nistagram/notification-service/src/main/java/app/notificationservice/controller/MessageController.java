package app.notificationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.notificationservice.service.MessageService;

@RestController
@RequestMapping(value = "api/message")
public class MessageController {

	private MessageService messageService;
	
	@Autowired
	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}
}
