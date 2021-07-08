package app.java.agentapp.client;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import app.java.agentapp.dto.AdDTO;
import app.java.agentapp.dto.AddCampaignMultipleDTO;
import app.java.agentapp.dto.AddCampaignOnceTimeDTO;
import app.java.agentapp.dto.CampaignDTO;


@FeignClient(name = "campaign-service", url = "http://localhost:8087/api/campaign/")
public interface CampaignClient {
	
	@GetMapping("/all/{username}")
	public Collection<CampaignDTO> getAllByUsername(@PathVariable("username") String username);

	@PostMapping("/once-time")
	public void createOnceTimeCampaign(@RequestBody AddCampaignOnceTimeDTO campaignDTO);
	
	@PostMapping("/multiple")
	public void createMultipleCampaign(@RequestBody AddCampaignMultipleDTO campaignDTO);
	
	@GetMapping("/future/{username}")
	public Collection<CampaignDTO> getFutureCampaignsByUsername(@PathVariable("username") String username);
	
	@PostMapping("/ad")
	public Long createAd(@RequestBody AdDTO dto);
	
	@PostMapping("/multiple/update")
	public void updateMultipleCampaign(@RequestBody CampaignDTO campaignDTO);
	
	@PutMapping("/{campaignId}")
	public void delete(@PathVariable("campaignId") long campaignId);
}
