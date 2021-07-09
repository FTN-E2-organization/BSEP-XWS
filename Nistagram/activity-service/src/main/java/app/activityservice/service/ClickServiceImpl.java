package app.activityservice.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.activityservice.dto.AddClickDTO;
import app.activityservice.dto.NumberOfClicksDTO;
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

	@Override
	public Collection<NumberOfClicksDTO> getNumberOfClicksByCampaignId(long campaignId) {
		Collection<Click> clicks = clickRepository.findAllByCampaignId(campaignId);
		Collection<NumberOfClicksDTO> dtos = new ArrayList<>();
		
		boolean exist = false;
		for (Click c : clicks) {			
			NumberOfClicksDTO dto = new NumberOfClicksDTO();			
			dto.ownerUsername = c.getOwner().getUsername();
			dto.ownerType = c.getOwnerType().toString();
			dto.numberOfClicks = clickRepository.getNumberOfClicksByUsernameAndOwnerType(c.getOwner().getUsername(), c.getOwnerType());
			for (NumberOfClicksDTO n : dtos) {
				if (n.ownerType.equals(c.getOwnerType().toString()) && n.ownerUsername.equals(c.getOwner().getUsername()))						
					exist = true;					
			}	
			if (!exist)
				dtos.add(dto);
			else
				exist = false;
		}						
		return dtos;
	}
	
	
	
}
