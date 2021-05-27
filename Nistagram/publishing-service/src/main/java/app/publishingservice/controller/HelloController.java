package app.publishingservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/hello")
public class HelloController {

	@GetMapping
	public ResponseEntity<?> hello() {
		return new ResponseEntity<String>("Hello from publishing service!", HttpStatus.OK);
	}
}
