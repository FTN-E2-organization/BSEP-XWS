package app.activityservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.activityservice.service.ClickService;

@RestController
@RequestMapping(value = "api/click")
public class ClickController {

	private ClickService clickService;
	
	@Autowired
	public ClickController(ClickService clickService) {
		this.clickService = clickService;
	}
}
