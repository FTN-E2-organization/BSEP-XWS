package app.activityservice.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.activityservice.dto.AddClickDTO;
import app.activityservice.enums.OwnerType;
import app.activityservice.model.Click;
import app.activityservice.repository.ClickRepository;
import app.activityservice.repository.ProfileRepository;

@Service
public class ClickServiceImpl implements ClickService {

	private ClickRepository clickRepository;
	private ProfileRepository profileRepository;
	
	@Autowired
	public ClickServiceImpl(ClickRepository clickRepository, ProfileRepository profileRepository) {
		this.clickRepository = clickRepository;
		this.profileRepository = profileRepository;
	}

	@Override
	public void create(AddClickDTO clickDTO) {
		Click click = new Click();
		click.setOwner(profileRepository.findByUsername(clickDTO.ownerUsername));
		click.setCampaignId(clickDTO.campaigId);
		click.setOwnerType(OwnerType.valueOf(clickDTO.ownerType));
		clickRepository.save(click);
	}

	@Override
	public Collection<Click> getAllByCampaignId(long campaignId) {
		return clickRepository.findAllByCampaignId(campaignId);
	}

	@Override
	public Collection<Click> getAll() {
		return clickRepository.findAll();
	}
	
}
