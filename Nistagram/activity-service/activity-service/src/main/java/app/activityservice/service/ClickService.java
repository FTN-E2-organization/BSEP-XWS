package app.activityservice.service;

import java.util.Collection;

import app.activityservice.dto.AddClickDTO;
import app.activityservice.model.Click;

public interface ClickService {

	void create(AddClickDTO clickDTO);

	Collection<Click> getAllByCampaignId(long campaignId);

}
