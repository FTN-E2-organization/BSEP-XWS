package app.campaignservice.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CampaignDTO {

	public long id;
	public String campaignType;
	public String contentType;
	public LocalDate startDate; 
	public LocalDate endDate;
	public String agentUsername;
	public int placementFrequency;
	public List<LocalTime> dailyFrequency;
	
	//lista reklama...
	
}
