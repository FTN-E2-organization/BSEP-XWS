package app.campaignservice.service;

import app.campaignservice.dto.AdDTO;

public interface AdService {

	long create(AdDTO dto);
	AdDTO getAddById(Long id);

	
}
