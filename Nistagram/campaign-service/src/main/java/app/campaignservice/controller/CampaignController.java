package app.campaignservice.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.campaignservice.dto.CampaignDTO;
import app.campaignservice.model.CustomPrincipal;
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
	
	@PreAuthorize("hasAuthority('campaignManagement')")
	@PostMapping(value = "/once-time", consumes = "application/json")
	public ResponseEntity<?> createOnceTimeCampaign(@RequestBody AddCampaignOnceTimeDTO campaignDTO) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
	        campaignDTO.agentUsername = principal.getUsername();
			campaignService.createOnceTimeCampaign(campaignDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while creating campaign. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@PreAuthorize("hasAuthority('campaignManagement')")
	@PostMapping(value = "/multiple", consumes = "application/json")
	public ResponseEntity<?> createMultipleCampaign(@RequestBody AddCampaignMultipleDTO campaignDTO) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
	        campaignDTO.agentUsername = principal.getUsername();
			campaignService.createMultipleCampaign(campaignDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while creating campaign. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
		
	@PreAuthorize("hasAuthority('campaignManagement')")
	@GetMapping("/future")
	public ResponseEntity<?> getFutureCampaignsByUsername(){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
	        String agentUsername = principal.getUsername();
			return new ResponseEntity<Collection<CampaignDTO>>(campaignService.getFutureCampaignsByUsername(agentUsername), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting future campaigns. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@PreAuthorize("hasAuthority('campaignManagement')")
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
	
	@PreAuthorize("hasAuthority('campaignManagement')")
	@GetMapping
	public ResponseEntity<?> getAllByUsername(){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
	        String agentUsername = principal.getUsername();
			return new ResponseEntity<Collection<CampaignDTO>>(campaignService.getAllByUsername(agentUsername), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting campaigns. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasAuthority('campaignManagement')")
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
	
	@GetMapping("/current/{username}")
	public ResponseEntity<?> getCurrentCampaignsByUsername(@PathVariable String username){
		try {
			return new ResponseEntity<Collection<CampaignDTO>>(campaignService.getCurrentCampaignsByUsername(username), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting current campaigns. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@GetMapping("/is-post/{id}")
	public ResponseEntity<?> isCampaignPost(@PathVariable Long id){
		try {
			return new ResponseEntity<Boolean>(campaignService.isCampaignPost(id), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting type of campaign. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCampaignById(@PathVariable Long id){
		try {
			return new ResponseEntity<CampaignDTO>(campaignService.getCampaignById(id), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting campaign. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@GetMapping("/current-category/{category}")
	public ResponseEntity<?> getCurrentCampaignsByCategory(@PathVariable String category){
		try {
			return new ResponseEntity<Collection<CampaignDTO>>(campaignService.getAllCurrentCampaignsByCategory(category), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting current campaigns. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@GetMapping("/all/{username}")
	public ResponseEntity<?> getAll(@PathVariable String username){
		try {
			return new ResponseEntity<Collection<CampaignDTO>>(campaignService.getAllByUsername(username), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting campaigns. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
}
