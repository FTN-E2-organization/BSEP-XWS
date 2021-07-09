package app.java.report.client;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import app.java.report.dto.CampaignDTO;


@FeignClient(name = "campaign-service", url = "http://localhost:8087/api/campaign/")
public interface CampaignClient {
	
	@GetMapping("/all-campaigns")
	public Collection<CampaignDTO> getAll();
}
