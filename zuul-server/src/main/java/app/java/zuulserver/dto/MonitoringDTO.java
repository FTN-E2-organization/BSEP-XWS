package app.java.zuulserver.dto;

import java.util.Collection;

public class MonitoringDTO {
	
	public long idCampaign;
	public String contentType;
	public String campaignType;
	public String categoryName;
	public String name;
	public int numberLikes;
	public int numberDislikes;
	public int numberComments;
	public int numberClicks;
	public Collection<NumberOfClicksDTO> numberOfClicksDTOs;

}
