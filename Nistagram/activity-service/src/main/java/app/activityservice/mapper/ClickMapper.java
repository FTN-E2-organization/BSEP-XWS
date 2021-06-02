package app.activityservice.mapper;

import java.util.ArrayList;
import java.util.Collection;

import app.activityservice.dto.ClickDTO;
import app.activityservice.model.Click;

public class ClickMapper {

	public static Collection<ClickDTO> toClickDTOs(Collection<Click> clicks) {
		Collection<ClickDTO> clickDTOs = new ArrayList<>();
		for (Click c : clicks) {
			ClickDTO dto = new ClickDTO();
			dto.campaigId = c.getCampaignId();
			dto.ownerType = c.getOwnerType().toString();
			dto.ownerUsername = c.getOwner().getUsername();
			clickDTOs.add(dto);
		}		
		return clickDTOs;
	}
	
}
