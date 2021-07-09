package app.java.report.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xmldb.api.modules.XMLResource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpStatus;

import app.java.report.client.AcivityClient;
import app.java.report.client.CampaignClient;
import app.java.report.dto.AdDTO;
import app.java.report.dto.CampaignDTO;
import app.java.report.dto.MonitoringDTO;
import app.java.report.dto.NumberOfReactionsDTO;
import app.java.report.dto.XmlDTO;
import app.java.report.service.ReportService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(value = "api/report", produces = MediaType.APPLICATION_JSON_VALUE, consumes =  MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class ReportController {

	private ReportService reportService;
	private AcivityClient activityClient;
	private CampaignClient campaignClient;
	
	public ReportController(ReportService reportService, AcivityClient activityClient, CampaignClient campaignClient) {
		this.reportService = reportService;
		this.activityClient = activityClient;
		this.campaignClient = campaignClient;
	}
	
	@PostMapping()
	public ResponseEntity<XmlDTO> getChangedXML(@RequestBody XmlDTO dto) throws Exception{
		String response = reportService.playWithXML(dto);
		return new ResponseEntity<XmlDTO>(new XmlDTO(response), HttpStatus.OK);
	}
	
	@PostMapping("/xml")
	public ResponseEntity<String> addMonitoring(@RequestBody XmlDTO dto) throws Exception{
		
		Collection<MonitoringDTO> monitoringDTOs = new ArrayList<>();
		Collection<CampaignDTO> campaignDTOs = this.campaignClient.getAll();
		for (CampaignDTO c : campaignDTOs) {	
			
			if (c.agentUsername.equals(dto.text)) {
				MonitoringDTO monitoringDTO = new MonitoringDTO();
				monitoringDTO.idCampaign = c.id;
				monitoringDTO.contentType = c.contentType;
				monitoringDTO.campaignType = c.campaignType;
				monitoringDTO.categoryName = c.categoryName;
				monitoringDTO.name = c.name;
				for (AdDTO a : c.ads) {
					NumberOfReactionsDTO numberOfReactionsDTO = this.activityClient.getNumberOfReactionsByAdId(a.id);
					monitoringDTO.numberLikes += numberOfReactionsDTO.numberOfLikes;
					monitoringDTO.numberDislikes += numberOfReactionsDTO.numberOfDislikes;
					monitoringDTO.numberComments += numberOfReactionsDTO.numberOfComments;
				}
				monitoringDTO.numberClick = this.activityClient.getAllClicksByCampaignId(c.id).size();				
				monitoringDTO.numberOfClicksDTOs = this.activityClient.getNumberOfClicksByCampaignId(c.id);
				monitoringDTOs.add(monitoringDTO);
			}
		}			
		List<MonitoringDTO> monitoring = new ArrayList<>(monitoringDTOs);
		reportService.saveFileFromString(monitoring);
		return new ResponseEntity<String>("Works",HttpStatus.OK);
		
	}
	
	@GetMapping(consumes = "application/json")
	public ResponseEntity<?> getReport() {
		try {
			XMLResource result = reportService.getFromFile();
			
			return new ResponseEntity<XMLResource>(result, HttpStatus.OK);
		} catch (Exception exception) {
			exception.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
