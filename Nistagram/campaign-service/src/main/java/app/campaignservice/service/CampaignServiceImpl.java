package app.campaignservice.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.campaignservice.dto.CampaignMultipleDTO;
import app.campaignservice.enums.CampaignType;
import app.campaignservice.enums.ContentType;
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
	public void createOnceTimeCampaign(CampaignMultipleDTO dto) {
		
	}

	@Override
	public void createMultipleCampaign(CampaignMultipleDTO dto) {
		//dodati za listu reklama
		
		Campaign campaign = new Campaign();
		campaign.setCampaignType(CampaignType.valueOf(dto.campaignType));
		campaign.setContentType(ContentType.valueOf(dto.campaignType));
		campaign.setStartDate(dto.startDate);
		campaign.setEndDate(dto.endDate);
		campaign.setDeleted(false);
		campaign.setLastUpdateTime(LocalDateTime.now());
		campaign.setAgentUsername(dto.agentUsername);
		campaign.setPlacementFrequency(dto.placementFrequency);
		
		Collection<LocalTime> dailyFrequency = new ArrayList<LocalTime>();
		for (LocalTime t : dto.dailyFrequency) {
			dailyFrequency.add(t);
		}
		campaign.setDailyFrequency(dailyFrequency);
		
		campaignRepository.save(campaign);
	}
	
	
}
