package app.java.agentapp.service;

import org.springframework.stereotype.Service;

import app.java.agentapp.repository.MonitoringRepository;

@Service
public class MonitoringService {

	private MonitoringRepository monitoringRepository;
	
	public MonitoringService(MonitoringRepository monitoringRepository) {
		this.monitoringRepository = monitoringRepository;
	}
	
	public void saveFileFromString(String text) throws Exception {
		monitoringRepository.saveMonitoring(text);
	}
}
