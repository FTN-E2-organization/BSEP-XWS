package app.campaignservice.dto;

import java.time.*;
import java.util.Collection;

public class CampaignDTO {

	public long id;
	public String contentType;
	public String campaignType;
	public String categoryName;
	public String name;
	
	public Collection<LocalTime> dailyFrequency;
	public LocalDate startDate;
	public LocalDate endDate;
	public boolean isDeleted;
	public LocalDateTime lastUpdateTime;
	
}
