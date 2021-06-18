package app.notificationservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/notification/hello")
public class HelloController {

	
	@GetMapping
	public String hello() {
		return "Hello world";
	}
}
