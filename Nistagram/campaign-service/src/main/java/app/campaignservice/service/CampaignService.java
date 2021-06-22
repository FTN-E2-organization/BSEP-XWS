package app.campaignservice.service;

import app.campaignservice.dto.CampaignDTO;

public interface CampaignService {

	void createOnceTimeCampaign(CampaignDTO campaignDTO);

	void createMultipleCampaign(CampaignDTO campaignDTO);
	
}
