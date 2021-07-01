package app.campaignservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.campaignservice.dto.AdDTO;
import app.campaignservice.model.Ad;
import app.campaignservice.repository.AdRepository;
import app.campaignservice.repository.CampaignRepository;

@Service
public class AdServiceImpl implements AdService {

	private AdRepository adRepository; 
	private CampaignRepository campaignRepository;
	
	@Autowired
	public AdServiceImpl(AdRepository adRepository, CampaignRepository campaignRepository) {
		this.adRepository = adRepository;
		this.campaignRepository = campaignRepository;
	}

	@Override
	public long create(AdDTO dto) {
		Ad ad = new Ad();
		ad.setProductLink(dto.productLink);		
		ad.setCampaign(campaignRepository.getById(dto.campaignId));
		Ad savedAd = adRepository.save(ad);
		return savedAd.getId();
	}
	
	
	
}
