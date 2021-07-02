package app.java.agentapp.service;

import app.java.agentapp.dto.AgentDTO;
import app.java.agentapp.model.Agent;

public interface AgentService {
	
	void createAgent(AgentDTO agentDTO) throws Exception;
	Agent findAgentById(Long id);
	void setHasApiToken(Long id, boolean hasToken);

}
