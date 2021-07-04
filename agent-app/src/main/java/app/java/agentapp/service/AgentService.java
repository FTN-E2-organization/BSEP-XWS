package app.java.agentapp.service;

import app.java.agentapp.dto.AgentDTO;
import app.java.agentapp.model.Agent;

public interface AgentService {
	
	void createAgent(AgentDTO agentDTO) throws Exception;
	Agent findAgentByUsername(String username);
	void setHasApiToken(String username, boolean hasToken);

}
