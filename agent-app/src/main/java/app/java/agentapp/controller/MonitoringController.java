package app.java.agentapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import app.java.agentapp.dto.XmlDTO;
import app.java.agentapp.service.MonitoringService;

@RestController
@RequestMapping(value = "api/monitoring")
@CrossOrigin
public class MonitoringController {

	private MonitoringService monitoringService;
	
	public MonitoringController(MonitoringService monitoringService) {
		this.monitoringService = monitoringService;
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<String> addMonitoring(@RequestBody XmlDTO dto) throws Exception{
		
		monitoringService.saveFileFromString(dto.getText());
		return new ResponseEntity<String>("Works",HttpStatus.OK);
		
	}
}
