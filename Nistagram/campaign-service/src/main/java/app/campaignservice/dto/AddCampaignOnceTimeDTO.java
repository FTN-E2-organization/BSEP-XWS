package app.campaignservice.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AddCampaignOnceTimeDTO {

	public long id;
	public String contentType;
	public LocalDate date; 
	public LocalTime time;
	public String agentUsername;
	public String categoryName;
	public String name;

}
