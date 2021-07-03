package app.java.agentapp.client;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import app.java.agentapp.dto.CampaignDTO;


@FeignClient(name = "campaign-service", url = "http://localhost:8087/api/campaign/")
public interface CampaignClient {

	@GetMapping("/{username}")
	public Collection<CampaignDTO> getAllByUsername(@PathVariable("username") String username);
}
