package app.java.agentapp.client;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import app.java.agentapp.dto.ClickDTO;
import app.java.agentapp.dto.NumberOfClicksDTO;
import app.java.agentapp.dto.NumberOfReactionsDTO;


@FeignClient(name = "activity-service", url = "http://localhost:8086/api/activity/")
public interface AcivityClient {

	@GetMapping("/reaction/ad/{id}")
	NumberOfReactionsDTO getNumberOfReactionsByAdId(@PathVariable("id") long id);
	
	@GetMapping("/click/{campaignId}/campaign-id")
	Collection<ClickDTO> getAllClicksByCampaignId(@PathVariable("campaignId") long campaignId);
	
	@GetMapping("/click/number-of-clicks/{campaignId}/campaign-id")
	Collection<NumberOfClicksDTO> getNumberOfClicksByCampaignId(@PathVariable long campaignId);
}
