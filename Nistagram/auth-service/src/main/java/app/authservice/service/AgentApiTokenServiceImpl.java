package app.authservice.service;

import java.util.Collection;
import java.util.UUID;

import javax.websocket.Decoder.Text;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.authservice.model.AgentApiToken;
import app.authservice.repository.AgentApiTokenRepository;

@Service
public class AgentApiTokenServiceImpl implements AgentApiTokenService{

	private AgentApiTokenRepository agentApiTokenRepository;
	
	@Autowired
	public AgentApiTokenServiceImpl(AgentApiTokenRepository agentApiTokenRepository) {
		this.agentApiTokenRepository = agentApiTokenRepository;
	}

	@Override
	public void saveAgentToken(String username, String jwtToken) {
		String savedToken = findTokenByUsername(username);
		if(savedToken == null) {
			AgentApiToken token = new AgentApiToken();
		
			token.setUsername(username);
			token.setToken(jwtToken);
		
			agentApiTokenRepository.save(token);
			
		}
	}

	@Override
	public boolean checkToken(String token) {
		Collection<AgentApiToken> tokens = agentApiTokenRepository.findAll();
		for(AgentApiToken t : tokens) {
			if(t.getToken().equals(token)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String findTokenByUsername(String username) {
		return agentApiTokenRepository.findTokenByUsername(username);
	}

}
