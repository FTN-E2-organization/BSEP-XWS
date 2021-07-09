package app.java.zuulserver.client;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import app.java.zuulserver.dto.ClickDTO;
import app.java.zuulserver.dto.NumberOfClicksDTO;
import app.java.zuulserver.dto.NumberOfReactionsDTO;
import app.java.zuulserver.dto.ReactionDTO;

@FeignClient(name = "activity-service")
public interface ActivityClient {

	@GetMapping("api/activity/reaction/likes/by-username/{username}")
	Collection<ReactionDTO> getLikesByUsername(@PathVariable("username") String username);

	@GetMapping("api/activity/reaction/dislikes/by-username/{username}")
	Collection<ReactionDTO> getDislikesByUsername(@PathVariable("username") String username);
	
	@GetMapping("api/activity/reaction/ad/{id}")
	NumberOfReactionsDTO getNumberOfReactionsByAdId(@PathVariable("id") long id);
	
	@GetMapping("api/activity/click/{campaignId}/campaign-id")
	Collection<ClickDTO> getAllClicksByCampaignId(@PathVariable("campaignId") long campaignId);
	
	@GetMapping("api/activity/click/number-of-clicks/{campaignId}/campaign-id")
	Collection<NumberOfClicksDTO> getNumberOfClicksByCampaignId(@PathVariable long campaignId);
	
}
