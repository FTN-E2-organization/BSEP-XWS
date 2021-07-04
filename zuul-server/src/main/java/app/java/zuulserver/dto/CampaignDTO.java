package app.java.zuulserver.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CampaignDTO {

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
	
}