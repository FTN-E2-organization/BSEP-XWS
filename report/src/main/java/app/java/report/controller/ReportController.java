package app.java.report.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.xmldb.api.modules.XMLResource;
import org.springframework.http.HttpStatus;
import app.java.report.dto.XmlDTO;
import app.java.report.model.Entitet;
import app.java.report.service.ReportService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(value = "api/report", produces = MediaType.APPLICATION_JSON_VALUE, consumes =  MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class ReportController {

	private ReportService reportService;
	
	public ReportController(ReportService reportService) {
		this.reportService = reportService;
	}
	
	@PostMapping()
	public ResponseEntity<XmlDTO> getChangedXML(@RequestBody XmlDTO dto) throws Exception{
		String response = reportService.playWithXML(dto);
		return new ResponseEntity<XmlDTO>(new XmlDTO(response), HttpStatus.OK);
	}
	
	@PostMapping("/xml")
	public ResponseEntity<String> addFakultet(@RequestBody XmlDTO dto) throws Exception{
		String s = reportService.playWithXML(dto);
		reportService.saveFileFromString(s);
		return new ResponseEntity<String>("Works",HttpStatus.OK);
		
	}
	
	@GetMapping(consumes = "application/json")
	public ResponseEntity<?> getReport() {
		try {
			XMLResource result = reportService.getFromFile();
			
			return new ResponseEntity<XMLResource>(result, HttpStatus.OK);
		} catch (Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
