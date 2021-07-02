package app.authservice.service;

public interface AgentApiTokenService {

	void saveAgentToken(String username);
	boolean checkToken(String token);
}
