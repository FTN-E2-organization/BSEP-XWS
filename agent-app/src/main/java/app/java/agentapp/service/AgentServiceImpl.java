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
		
		agentRepository.save(agent);
	}

	@Override
	public Agent findAgentById(Long id) {
		return agentRepository.findAgentById(id);
	}

}
