package app.java.agentapp.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MonitoringDTO {
	
	public long idCampaign;
	public String contentType;
	public String campaignType;
	public String categoryName;
	public String name;
	public int numberLikes;
	public int numberDislikes;
	public int numberComments;
	public int numberClick;
}
