package app.campaignservice.service;

import java.util.Collection;

import app.campaignservice.dto.CampaignDTO;
import app.campaignservice.dto.CampaignMultipleDTO;
import app.campaignservice.dto.CampaignOnceTimeDTO;

public interface CampaignService {

	void createMultipleCampaign(CampaignMultipleDTO campaignDTO);

	void createOnceTimeCampaign(CampaignOnceTimeDTO campaignDTO);

	Collection<CampaignDTO> getAllByUsername(String username);
	
}
