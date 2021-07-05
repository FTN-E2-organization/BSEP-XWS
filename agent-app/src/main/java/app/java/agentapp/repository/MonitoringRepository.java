package app.java.agentapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.java.agentapp.db.ExistManager;

@Repository
public class MonitoringRepository {
	private String collectionId = "/db/monitoring";
	@Autowired
	private ExistManager existManager;
	
	public void saveMonitoring(String text) throws Exception {
		existManager.storeFromText(collectionId, "CampaignMonitoring", text);
	}
}
