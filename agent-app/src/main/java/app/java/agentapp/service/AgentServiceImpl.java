package app.java.agentapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.java.agentapp.dto.AgentDTO;
import app.java.agentapp.exception.BadRequest;
import app.java.agentapp.model.Agent;
import app.java.agentapp.repository.AgentRepository;

@Service
public class AgentServiceImpl implements AgentService{
	
	private AgentRepository agentRepository;
	
	@Autowired
	public AgentServiceImpl(AgentRepository agentRepository) {
		this.agentRepository = agentRepository;
	}

	@Override
	public void createAgent(AgentDTO agentDTO) throws Exception {

		if(agentRepository.existsByUsername(agentDTO.username)) {
			throw new BadRequest("Username is busy.");
		}
		Agent agent = new Agent();
		agent.setUsername(agentDTO.username);
		agent.setEmail(agentDTO.email);
		agent.setPassword(agentDTO.password);
		agent.setHasApiToken(false);
		
		agentRepository.save(agent);
	}

	@Override
	public Agent findAgentByUsername(String username) {
		return agentRepository.findByUsername(username);
	}

	@Override
	public void setHasApiToken(String username, boolean hasToken) {
		Agent agent = agentRepository.findByUsername(username);
		
		agent.setHasApiToken(hasToken);
		
		agentRepository.save(agent);
	}

	@Override
	public boolean hasToken(String username) {
		Agent agent = agentRepository.findByUsername(username);
		return agent.isHasApiToken();
	}

	@Override
	public void setTokenAgent(String username, String token) {
		Agent agent = agentRepository.findByUsername(username);
		
		agent.setToken(token);
		
		agentRepository.save(agent);
	}

	@Override
	public String getTokenAgent(String username) {
		Agent agent = agentRepository.findByUsername(username);
		return agent.getToken();
	}


}
