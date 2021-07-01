package app.campaignservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.campaignservice.dto.CampaignMultipleDTO;
import app.campaignservice.dto.CampaignOnceTimeDTO;
import app.campaignservice.service.CampaignService;

@RestController
@RequestMapping(value = "api/campaign")
public class CampaignController {

	private CampaignService campaignService;
	
	@Autowired
	public CampaignController(CampaignService campaignService) {
		this.campaignService = campaignService;
	}
	
	@PostMapping(value = "/once-time", consumes = "application/json")
	public ResponseEntity<?> createOnceTimeCampaign(@RequestBody CampaignOnceTimeDTO campaignDTO) {
		try {
			campaignService.createOnceTimeCampaign(campaignDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while creating campaign. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@PostMapping(value = "/multiple", consumes = "application/json")
	public ResponseEntity<?> createMultipleCampaign(@RequestBody CampaignMultipleDTO campaignDTO) {
		try {
//			campaignService.createMultipleCampaign(campaignDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while creating campaign. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
}
