package app.campaignservice.service;

import app.campaignservice.dto.CampaignMultipleDTO;
import app.campaignservice.dto.CampaignOnceTimeDTO;

public interface CampaignService {

	void createMultipleCampaign(CampaignMultipleDTO campaignDTO);

	void createOnceTimeCampaign(CampaignOnceTimeDTO campaignDTO);
	
}
