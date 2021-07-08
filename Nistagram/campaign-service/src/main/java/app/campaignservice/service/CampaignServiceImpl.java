package app.campaignservice.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.campaignservice.dto.CampaignDTO;
import app.campaignservice.dto.CampaignRequestDTO;
import app.campaignservice.dto.InfluenceRequestDTO;
import app.campaignservice.dto.AdDTO;
import app.campaignservice.dto.AddCampaignMultipleDTO;
import app.campaignservice.dto.AddCampaignOnceTimeDTO;
import app.campaignservice.enums.CampaignType;
import app.campaignservice.enums.ContentType;
import app.campaignservice.model.Ad;
import app.campaignservice.model.Campaign;
import app.campaignservice.model.InfluenceRequest;
import app.campaignservice.repository.CampaignRepository;
import app.campaignservice.repository.InfluenceRequestRepository;
import app.campaignservice.repository.ProfileRepository;

@Service
public class CampaignServiceImpl implements CampaignService {

	private CampaignRepository campaignRepository;
	private ProfileRepository profileRepository;
	private InfluenceRequestRepository influenceRequestRepository;

	@Autowired
	public CampaignServiceImpl(CampaignRepository campaignRepository, ProfileRepository profileRepository,
			InfluenceRequestRepository influenceRequestRepository) {
		this.campaignRepository = campaignRepository;
		this.profileRepository = profileRepository;
		this.influenceRequestRepository = influenceRequestRepository;
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
			dto.agentUsername = c.getAgentUsername();

			dto.dailyFrequency = c.getDailyFrequency();
			dto.startDate = c.getStartDate();
			dto.endDate = c.getEndDate();
			dto.isDeleted = c.isDeleted();
			dto.lastUpdateTime = c.getLastUpdateTime();
			Collection<AdDTO> adDTOs = new ArrayList<>();
			for (Ad a : c.getAds()) {
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
			// da bi mogla testirati, jer ni jedna ne upada u ovaj interval
			// if (c.getStartDate().isBefore(LocalDate.now()) &&
			// c.getEndDate().isAfter(LocalDate.now()) && !c.isDeleted() && ) {
			// for(LocalTime l : c.getDailyFrequency()) {
			// if(l.equals(LocalTime.now())) {
			CampaignDTO dto = new CampaignDTO();
			dto.id = c.getId();
			dto.contentType = c.getContentType().toString();
			dto.campaignType = c.getCampaignType().toString();
			dto.categoryName = c.getCategoryName();
			dto.name = c.getName();
			Collection<AdDTO> adDTOs = new ArrayList<>();
			for (Ad a : c.getAds()) {
				adDTOs.add(new AdDTO(a.getId(), a.getProductLink(), a.getCampaign().getId()));
			}
			dto.ads = adDTOs;
			dtos.add(dto);
			// }
			// }
			// }
		}
		return dtos;
	}

	@Override
	public boolean isCampaignPost(Long id) {
		Campaign campaign = campaignRepository.getById(id);
		if (campaign.getContentType().equals(ContentType.POST)) {
			return true;
		}
		return false;
	}

	@Override
	public CampaignDTO getCampaignById(Long id) {
		Campaign c = campaignRepository.getById(id);
		CampaignDTO dto = new CampaignDTO();
		dto.id = c.getId();
		dto.contentType = c.getContentType().toString();
		dto.campaignType = c.getCampaignType().toString();
		dto.categoryName = c.getCategoryName();
		dto.name = c.getName();
		Collection<AdDTO> adDTOs = new ArrayList<>();
		for (Ad a : c.getAds()) {
			adDTOs.add(new AdDTO(a.getId(), a.getProductLink(), a.getCampaign().getId()));
		}
		dto.ads = adDTOs;
		return dto;
	}

	@Override
	public Collection<CampaignDTO> getAllCurrentCampaignsByCategory(String category) {
		Collection<Campaign> campaigns = campaignRepository.findAll();
		Collection<CampaignDTO> dtos = new ArrayList<>();
		for (Campaign c : campaigns) {
			// c.getStartDate().isBefore(LocalDate.now()) &&
			// c.getEndDate().isAfter(LocalDate.now()) &&
			if (!c.isDeleted() && c.getCategoryName().equals(category)) {
				CampaignDTO dto = new CampaignDTO();
				dto.id = c.getId();
				dto.contentType = c.getContentType().toString();
				dto.campaignType = c.getCampaignType().toString();
				dto.categoryName = c.getCategoryName();
				dto.name = c.getName();
				Collection<AdDTO> adDTOs = new ArrayList<>();
				for (Ad a : c.getAds()) {
					adDTOs.add(new AdDTO(a.getId(), a.getProductLink(), a.getCampaign().getId()));
				}
				dto.ads = adDTOs;
				dtos.add(dto);
			}
		}
		return dtos;
	}

	@Override
	public void createInfluenceRequest(InfluenceRequestDTO requestDTO) {
		InfluenceRequest request = new InfluenceRequest();
		request.setCampaign(campaignRepository.getById(requestDTO.campaignId));
		request.setProfile(profileRepository.findByUsername(requestDTO.profileUsername));
		influenceRequestRepository.save(request);

	}

	@Override
	public void judgeInfluenceRequest(InfluenceRequestDTO requestDTO) {
		InfluenceRequest request = influenceRequestRepository.getById(requestDTO.id);
		request.setIsApproved(requestDTO.isApproved);
		influenceRequestRepository.save(request);
	}

	@Override
	public Collection<CampaignRequestDTO> findAllCampaignRequestsByInfluencer(String username) {
		Collection<InfluenceRequest> requests = influenceRequestRepository.findByProfileUsername(username);
		Collection<InfluenceRequest> answeredRequests = requests.stream().filter(r -> r.getIsApproved() != null)
				.collect(Collectors.toList());

		Collection<CampaignRequestDTO> dtos = new ArrayList<>();
		for (InfluenceRequest i : requests) {
			boolean flag = false;
			for (CampaignRequestDTO c : dtos) {
				if (i.getCampaign().getId() == c.id) {
					flag = true;
				}
			}
			if (flag) {
				continue;
			}
			InfluenceRequest r = answeredRequests.stream().filter(re -> re.getCampaign().getId() == i.getCampaign().getId())
					.findFirst().orElse(null);
			if (r != null) {
				continue;
			}
			CampaignRequestDTO dto = new CampaignRequestDTO();
			dto.id = i.getCampaign().getId();
			dto.contentType = i.getCampaign().getContentType().toString();
			dto.campaignType = i.getCampaign().getCampaignType().toString();
			dto.categoryName = i.getCampaign().getCategoryName();
			dto.name = i.getCampaign().getName();
			dto.dailyFrequency = i.getCampaign().getDailyFrequency();
			dto.startDate = i.getCampaign().getStartDate();
			dto.endDate = i.getCampaign().getEndDate();
			dto.requestId = i.getId();
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public Collection<CampaignDTO> findAcceptedCurrentCampaignRequestsByInfluencer(String username) {
		Collection<InfluenceRequest> requests = influenceRequestRepository.findByProfileUsernameAndIsApproved(username,true);
		Collection<Campaign> campaigns = new ArrayList<>();
		for (InfluenceRequest i : requests) {
			campaigns.add(i.getCampaign());
		}
		Collection<CampaignDTO> dtos = new ArrayList<>();
		for (Campaign c : campaigns) {
			 if (!c.getStartDate().isAfter(LocalDate.now()) &&
				 !c.getEndDate().isBefore(LocalDate.now()) && !c.isDeleted() ) {
				 	for(LocalTime l : c.getDailyFrequency()) {
						if(l.equals(LocalTime.now())) {
							CampaignDTO dto = new CampaignDTO();
							dto.id = c.getId();
							dto.contentType = c.getContentType().toString();
							dto.campaignType = c.getCampaignType().toString();
							dto.categoryName = c.getCategoryName();
							dto.name = c.getName();
							Collection<AdDTO> adDTOs = new ArrayList<>();
							for (Ad a : c.getAds()) {
								adDTOs.add(new AdDTO(a.getId(), a.getProductLink(), a.getCampaign().getId()));
							}
							dto.ads = adDTOs;
							dtos.add(dto);
						}
				 }
			}
		}
		return dtos;
	}

}
