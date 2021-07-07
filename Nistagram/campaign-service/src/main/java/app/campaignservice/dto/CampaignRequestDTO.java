package app.campaignservice.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

public class CampaignRequestDTO {
	public long id;
	public String contentType;
	public String campaignType;
	public String categoryName;
	public String name;
	
	public Collection<LocalTime> dailyFrequency;
	public Collection<AdDTO> ads;
	public LocalDate startDate;
	public LocalDate endDate;
	public boolean isDeleted;
	public LocalDateTime lastUpdateTime;
	
	public long requestId;
}
