package app.activityservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.activityservice.dto.AddClickDTO;
import app.activityservice.service.ClickService;

@RestController
@RequestMapping(value = "api/click")
public class ClickController {

	private ClickService clickService;
	
	@Autowired
	public ClickController(ClickService clickService) {
		this.clickService = clickService;
	}	
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody AddClickDTO clickDTO){
		try {
			/*Username trenutno ulogovanog korisnika ce se preuzeti iz tokena*/
			
			clickService.create(clickDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	

	@GetMapping("/{campaignId}/campaign-id")
	public ResponseEntity<?> getAllByCampaignId(@PathVariable long campaignId){
		try {
			/*Username trenutno ulogovanog korisnika ce se preuzeti iz tokena*/
			return new ResponseEntity<>(clickService.getAllByCampaignId(campaignId), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
}
