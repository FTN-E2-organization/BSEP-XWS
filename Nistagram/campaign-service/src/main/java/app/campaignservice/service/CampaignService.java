package app.campaignservice.service;

import app.campaignservice.dto.CampaignMultipleDTO;

public interface CampaignService {

	void createOnceTimeCampaign(CampaignMultipleDTO campaignDTO);

	void createMultipleCampaign(CampaignMultipleDTO campaignDTO);
	
}
