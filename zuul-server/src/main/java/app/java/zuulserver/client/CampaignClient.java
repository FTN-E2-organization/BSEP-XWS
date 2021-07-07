package app.java.zuulserver.client;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import app.java.zuulserver.dto.AdDTO;
import app.java.zuulserver.dto.CampaignDTO;

@FeignClient(name = "campaign-service")
public interface CampaignClient {

	@GetMapping("api/campaign/current/{username}")
	public Collection<CampaignDTO> getCurrentCampaignsByUsername(@PathVariable("username") String username);
	
	@GetMapping("api/campaign/ad/{id}")
	public AdDTO getAdById(@PathVariable("id") Long id);
	
	@GetMapping("api/campaign/current-category/{category}")
	public Collection<CampaignDTO> getCurrentCampaignsByCategory(@PathVariable("category") String category);

	@GetMapping("api/campaign/accepted/requests/{username}")
	public Collection<CampaignDTO> getCurrentCampaignsByInfluencerUsername(String username);

}
