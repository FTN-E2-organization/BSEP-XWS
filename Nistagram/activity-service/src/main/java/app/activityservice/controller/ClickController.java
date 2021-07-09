package app.activityservice.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.activityservice.dto.AddClickDTO;
import app.activityservice.dto.NumberOfClicksDTO;
import app.activityservice.mapper.ClickMapper;
import app.activityservice.service.ClickService;
import app.activityservice.model.CustomPrincipal;

@RestController
@RequestMapping(value = "api/activity/click")
public class ClickController {

	private ClickService clickService;
	
	@Autowired
	public ClickController(ClickService clickService) {
		this.clickService = clickService;
	}	

	@PreAuthorize("hasAuthority('createClick')")
	@PostMapping
	public ResponseEntity<?> create(@RequestBody AddClickDTO clickDTO){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
	        clickDTO.ownerUsername = principal.getUsername();
			
			clickService.create(clickDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while creating click. " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@PreAuthorize("hasAuthority('getClicks')")
	@GetMapping
	public ResponseEntity<?> getAll(){
		try {
			return new ResponseEntity<>(ClickMapper.toClickDTOs(clickService.getAll()), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting clicks.", HttpStatus.BAD_REQUEST);
		}
	}	

	@GetMapping("/{campaignId}/campaign-id")
	public ResponseEntity<?> getAllByCampaignId(@PathVariable long campaignId){
		try {
			return new ResponseEntity<>(ClickMapper.toClickDTOs(clickService.getAllByCampaignId(campaignId)), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting clicks.", HttpStatus.BAD_REQUEST);
		}
	}	
	
	@GetMapping("/number-of-clicks/{campaignId}/campaign-id")
	public ResponseEntity<?> getNumberOfClicksByCampaignId(@PathVariable long campaignId){
		try {
			return new ResponseEntity<Collection<NumberOfClicksDTO>>(clickService.getNumberOfClicksByCampaignId(campaignId), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting clicks.", HttpStatus.BAD_REQUEST);
		}
	}	
	
	
}
