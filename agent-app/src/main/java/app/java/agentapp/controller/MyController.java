package app.java.agentapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/controller")
public class MyController {

	@GetMapping("/hello")
	public String findProfileByUsername(){
		return "Hello World";
	}

}
