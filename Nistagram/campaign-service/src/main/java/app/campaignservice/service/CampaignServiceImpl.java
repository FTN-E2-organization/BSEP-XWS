package app.campaignservice.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.campaignservice.dto.CampaignDTO;
import app.campaignservice.dto.AddCampaignMultipleDTO;
import app.campaignservice.dto.AddCampaignOnceTimeDTO;
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
	public void createMultipleCampaign(AddCampaignMultipleDTO dto) {
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


	@Override
	public void createOnceTimeCampaign(AddCampaignOnceTimeDTO campaignDTO) {
		Collection<LocalTime> dailyFrequency = new ArrayList<>();
		dailyFrequency.add(campaignDTO.time.plusHours(1)); //ne upise u bazu dobro ako ne dodam 1
		
		Campaign campaign = new Campaign();
		campaign.setCampaignType(CampaignType.ONCE_TIME);
		campaign.setContentType(ContentType.valueOf(campaignDTO.contentType.toUpperCase()));
		campaign.setStartDate(campaignDTO.date);
		campaign.setEndDate(campaignDTO.date);
		campaign.setDeleted(false);
		campaign.setLastUpdateTime(LocalDateTime.now());
		campaign.setAgentUsername(campaignDTO.agentUsername);
		campaign.setCategoryName(campaignDTO.categoryName);
		campaign.setPlacementFrequency(0);
		campaign.setDailyFrequency(dailyFrequency);
		campaignRepository.save(campaign);
	}


	@Override
	public Collection<CampaignDTO> getAllByUsername(String username) {
		Collection<Campaign> campaigns = campaignRepository.findByAgentUsername(username);
		Collection<CampaignDTO> dtos = new ArrayList<>();
		for (Campaign c : campaigns) {
			if (c.getStartDate().isAfter(LocalDate.now())) {
				CampaignDTO dto = new CampaignDTO();
				dto.id = c.getId();
				dto.contentType = c.getContentType().toString();
				dto.campaignType = c.getCampaignType().toString();
				dto.categoryName = c.getCategoryName();
				dtos.add(dto);
			}
		}		
		return dtos;
	}
	
	
}
