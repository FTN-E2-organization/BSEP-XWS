package app.campaignservice.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.campaignservice.dto.CampaignDTO;
import app.campaignservice.dto.AdDTO;
import app.campaignservice.dto.AddCampaignMultipleDTO;
import app.campaignservice.dto.AddCampaignOnceTimeDTO;
import app.campaignservice.enums.CampaignType;
import app.campaignservice.enums.ContentType;
import app.campaignservice.model.Ad;
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
		Campaign campaign = new Campaign();
		campaign.setCampaignType(CampaignType.MULTIPLE);
		campaign.setContentType(ContentType.valueOf(dto.contentType.toUpperCase()));
		campaign.setStartDate(dto.startDate);
		campaign.setEndDate(dto.endDate);
		campaign.setDeleted(false);
		campaign.setName(dto.name);
		campaign.setCategoryName(dto.categoryName);
		campaign.setLastUpdateTime(LocalDateTime.now());
		campaign.setAgentUsername(dto.agentUsername);
		campaign.setPlacementFrequency(dto.dailyFrequency.size());
		
		Collection<LocalTime> dailyFrequency = new ArrayList<LocalTime>();
		for (LocalTime t : dto.dailyFrequency) {
			dailyFrequency.add(t);
		}
		campaign.setDailyFrequency(dailyFrequency);
		campaignRepository.save(campaign);
	}


	@Override
	public void createOnceTimeCampaign(AddCampaignOnceTimeDTO dto) {
		Collection<LocalTime> dailyFrequency = new ArrayList<>();
		dailyFrequency.add(dto.time);
		
		Campaign campaign = new Campaign();
		campaign.setCampaignType(CampaignType.ONCE_TIME);
		campaign.setContentType(ContentType.valueOf(dto.contentType.toUpperCase()));
		campaign.setStartDate(dto.date);
		campaign.setEndDate(dto.date);
		campaign.setDeleted(false);
		campaign.setLastUpdateTime(LocalDateTime.now());
		campaign.setAgentUsername(dto.agentUsername);
		campaign.setCategoryName(dto.categoryName);
		campaign.setPlacementFrequency(1);
		campaign.setName(dto.name);
		campaign.setDailyFrequency(dailyFrequency);
		campaignRepository.save(campaign);
	}


	@Override
	public Collection<CampaignDTO> getFutureCampaignsByUsername(String username) {
		Collection<Campaign> campaigns = campaignRepository.findByAgentUsername(username);
		Collection<CampaignDTO> dtos = new ArrayList<>();
		for (Campaign c : campaigns) {
			if (c.getStartDate().isAfter(LocalDate.now()) && !c.isDeleted()) {
				CampaignDTO dto = new CampaignDTO();
				dto.id = c.getId();
				dto.contentType = c.getContentType().toString();
				dto.campaignType = c.getCampaignType().toString();
				dto.categoryName = c.getCategoryName();
				dto.name = c.getName();
				dtos.add(dto);
			}
		}		
		return dtos;
	}


	@Override
	public void delete(long campaignId) {
		Campaign campaign = campaignRepository.getById(campaignId);
		campaign.setDeleted(true);
		campaignRepository.save(campaign);
	}


	@Override
	public Collection<CampaignDTO> getAllByUsername(String username) {
		Collection<Campaign> campaigns = campaignRepository.findByAgentUsername(username);
		Collection<CampaignDTO> dtos = new ArrayList<>();
		
		for (Campaign c : campaigns) {
			CampaignDTO dto = new CampaignDTO();
			dto.id = c.getId();
			dto.contentType = c.getContentType().toString().toLowerCase();
			dto.campaignType = c.getCampaignType().toString().toLowerCase();
			dto.categoryName = c.getCategoryName();
			dto.name = c.getName();
			
			dto.dailyFrequency = c.getDailyFrequency();
			dto.startDate = c.getStartDate();
			dto.endDate = c.getEndDate();
			dto.isDeleted = c.isDeleted();
			dto.lastUpdateTime = c.getLastUpdateTime();
			Collection<AdDTO> adDTOs = new ArrayList<>();
			for(Ad a: c.getAds()) {
				adDTOs.add(new AdDTO(a.getId(), a.getProductLink(), a.getCampaign().getId()));
			}
			dto.ads = adDTOs;
			dtos.add(dto);
		}				
		return dtos;
	}


	@Override
	public void updateMultipleCampaign(CampaignDTO dto) {
		Campaign campaign = campaignRepository.getById(dto.id);
		
		if (campaign.getLastUpdateTime().plusHours(24).isAfter(LocalDateTime.now())) {
			return;
		}		
		campaign.setStartDate(dto.startDate);
		campaign.setEndDate(dto.endDate);
		campaign.setName(dto.name);
		campaign.setLastUpdateTime(LocalDateTime.now());
		campaign.setPlacementFrequency(dto.dailyFrequency.size());
		
		Collection<LocalTime> dailyFrequency = new ArrayList<LocalTime>();
		for (LocalTime t : dto.dailyFrequency) {
			dailyFrequency.add(t);
		}
		campaign.setDailyFrequency(dailyFrequency);
		
		campaignRepository.save(campaign);
	}


	@Override
	public Collection<CampaignDTO> getCurrentCampaignsByUsername(String username) {
		Collection<Campaign> campaigns = campaignRepository.findByAgentUsername(username);
		Collection<CampaignDTO> dtos = new ArrayList<>();
		for (Campaign c : campaigns) {
			if (c.getStartDate().isBefore(LocalDate.now()) && c.getEndDate().isAfter(LocalDate.now())  && !c.isDeleted()) {
				CampaignDTO dto = new CampaignDTO();
				dto.id = c.getId();
				dto.contentType = c.getContentType().toString();
				dto.campaignType = c.getCampaignType().toString();
				dto.categoryName = c.getCategoryName();
				dto.name = c.getName();
				Collection<AdDTO> adDTOs = new ArrayList<>();
				for(Ad a: c.getAds()) {
					adDTOs.add(new AdDTO(a.getId(), a.getProductLink(), a.getCampaign().getId()));
				}
				dto.ads = adDTOs;
				dtos.add(dto);
			}
		}		
		return dtos;
	}
	
	
}
