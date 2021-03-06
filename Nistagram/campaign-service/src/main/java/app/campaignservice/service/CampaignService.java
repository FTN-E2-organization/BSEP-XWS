package app.campaignservice.service;

import java.util.Collection;

import app.campaignservice.dto.CampaignDTO;
import app.campaignservice.dto.CampaignRequestDTO;
import app.campaignservice.dto.InfluenceRequestDTO;
import app.campaignservice.dto.AddCampaignMultipleDTO;
import app.campaignservice.dto.AddCampaignOnceTimeDTO;

public interface CampaignService {

	void createMultipleCampaign(AddCampaignMultipleDTO campaignDTO);

	void createOnceTimeCampaign(AddCampaignOnceTimeDTO campaignDTO);

	Collection<CampaignDTO> getFutureCampaignsByUsername(String username);

	void delete(long campaignId);

	Collection<CampaignDTO> getAllByUsername(String username);

	void updateMultipleCampaign(CampaignDTO campaignDTO);
	
	Collection<CampaignDTO> getCurrentCampaignsByUsername(String username);
	
	boolean isCampaignPost(Long id);
	
	CampaignDTO getCampaignById(Long id);
	
	Collection<CampaignDTO> getAllCurrentCampaignsByCategory(String category);

	void createInfluenceRequest(InfluenceRequestDTO requestDTO);

	void judgeInfluenceRequest(InfluenceRequestDTO requestDTO);

	Collection<CampaignRequestDTO> findAllCampaignRequestsByInfluencer(String username);

	Collection<CampaignDTO> findAcceptedCurrentCampaignRequestsByInfluencer(String username);

	Collection<CampaignDTO> getAll();
}
