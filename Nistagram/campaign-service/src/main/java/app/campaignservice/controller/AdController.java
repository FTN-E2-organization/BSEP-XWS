package app.campaignservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.campaignservice.dto.AdDTO;
import app.campaignservice.service.AdService;

@RestController
@RequestMapping(value = "api/campaign/ad")
public class AdController {

	private AdService adService;
	
	@Autowired
	public AdController(AdService adService) {
		this.adService = adService;
	}
		
	@PreAuthorize("hasAuthority('campaignManagement')")
	@PostMapping()
	public ResponseEntity<?> create(@RequestBody AdDTO dto) {
		try {
			long adId = adService.create(dto);
			return new ResponseEntity<>(adId, HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while creating ad. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	
}
