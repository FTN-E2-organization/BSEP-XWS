package app.campaignservice.service;

import java.util.Collection;

import app.campaignservice.dto.CampaignDTO;
import app.campaignservice.dto.AddCampaignMultipleDTO;
import app.campaignservice.dto.AddCampaignOnceTimeDTO;

public interface CampaignService {

	void createMultipleCampaign(AddCampaignMultipleDTO campaignDTO);

	void createOnceTimeCampaign(AddCampaignOnceTimeDTO campaignDTO);

	Collection<CampaignDTO> getFutureCampaignsByUsername(String username);

	void delete(long campaignId);

	Collection<CampaignDTO> getAllByUsername(String username);
	
}
