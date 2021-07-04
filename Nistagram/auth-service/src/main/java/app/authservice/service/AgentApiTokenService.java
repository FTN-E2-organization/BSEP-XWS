package app.authservice.service;

public interface AgentApiTokenService {

	void saveAgentToken(String username, String jwtToken);
	boolean checkToken(String token);
	String findTokenByUsername(String username);
}
