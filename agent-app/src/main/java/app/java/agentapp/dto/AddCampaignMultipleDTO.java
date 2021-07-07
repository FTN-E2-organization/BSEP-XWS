package app.java.agentapp.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddCampaignMultipleDTO {

	public long id;
	public String campaignType;
	public String contentType;
	public LocalDate startDate; 
	public LocalDate endDate;
	public String agentUsername;
	public int placementFrequency;
	public ArrayList<LocalTime> dailyFrequency;
	public String categoryName;
	public String name;
	
}