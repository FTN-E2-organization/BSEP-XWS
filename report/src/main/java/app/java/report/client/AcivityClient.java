package app.java.report.client;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import app.java.report.dto.ClickDTO;
import app.java.report.dto.NumberOfClicksDTO;
import app.java.report.dto.NumberOfReactionsDTO;


@FeignClient(name = "activity-service", url = "${client.activity}")
public interface AcivityClient {

	@GetMapping("/reaction/ad/{id}")
	NumberOfReactionsDTO getNumberOfReactionsByAdId(@PathVariable("id") long id);
	
	@GetMapping("/click/{campaignId}/campaign-id")
	Collection<ClickDTO> getAllClicksByCampaignId(@PathVariable("campaignId") long campaignId);
	
	@GetMapping("/click/number-of-clicks/{campaignId}/campaign-id")
	Collection<NumberOfClicksDTO> getNumberOfClicksByCampaignId(@PathVariable long campaignId);
}