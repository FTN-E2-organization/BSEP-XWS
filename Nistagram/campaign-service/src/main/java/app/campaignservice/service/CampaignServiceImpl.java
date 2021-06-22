package app.campaignservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.campaignservice.dto.CampaignDTO;
import app.campaignservice.model.Campaign;
import app.campaignservice.repository.CampaignRepository;

@Service
public class CampaignServiceImpl implements CampaignService {

	private CampaignRepository campaignRepository;
	
	@Autowired
	public CampaignServiceImpl(CampaignRepository campaignRepository) {
		this.campaignRepository = campaignRepository;		
	}

	@Override
	public void createOnceTimeCampaign(CampaignDTO campaignDTO) {
		Campaign campaign = new Campaign();
			
	}

	@Override
	public void createMultipleCampaign(CampaignDTO campaignDTO) {
		// TODO Auto-generated method stub
		
	}
	
	
}
