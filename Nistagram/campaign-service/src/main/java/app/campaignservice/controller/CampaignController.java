package app.campaignservice.controller;

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

import app.campaignservice.dto.CampaignDTO;
import app.campaignservice.dto.AddCampaignMultipleDTO;
import app.campaignservice.dto.AddCampaignOnceTimeDTO;
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
	public ResponseEntity<?> createOnceTimeCampaign(@RequestBody AddCampaignOnceTimeDTO campaignDTO) {
		try {
			campaignService.createOnceTimeCampaign(campaignDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while creating campaign. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@PostMapping(value = "/multiple", consumes = "application/json")
	public ResponseEntity<?> createMultipleCampaign(@RequestBody AddCampaignMultipleDTO campaignDTO) {
		try {
			campaignService.createMultipleCampaign(campaignDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while creating campaign. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
		
	@GetMapping("/future/{username}")
	public ResponseEntity<?> getFutureCampaignsByUsername(@PathVariable String username){
		try {
			return new ResponseEntity<Collection<CampaignDTO>>(campaignService.getFutureCampaignsByUsername(username), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting future campaigns. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@PutMapping(value = "/{campaignId}")
	public ResponseEntity<?> delete(@PathVariable long campaignId) {
		try {
			campaignService.delete(campaignId);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while deleting campaign. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@GetMapping("/{username}")
	public ResponseEntity<?> getAllByUsername(@PathVariable String username){
		try {
			return new ResponseEntity<Collection<CampaignDTO>>(campaignService.getAllByUsername(username), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting campaigns. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/multiple/update", consumes = "application/json")
	public ResponseEntity<?> updateMultipleCampaign(@RequestBody CampaignDTO campaignDTO) {
		try {
			campaignService.updateMultipleCampaign(campaignDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while updating campaign. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
