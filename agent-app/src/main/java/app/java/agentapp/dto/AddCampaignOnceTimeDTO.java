package app.java.agentapp.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddCampaignOnceTimeDTO {

	public long id;
	public String contentType;
	public LocalDate date; 
	public LocalTime time;
	public String agentUsername;
	public String categoryName;
	public String name;

}
