package app.authservice.service;

import java.util.Collection;

import app.authservice.dto.AgentRegistrationRequestDTO;
import app.authservice.model.AgentRegistrationRequest;

public interface AgentRegistrationService {

	void create(AgentRegistrationRequestDTO requestDTO);
	Collection<AgentRegistrationRequest> getAllDisapproved();
	void approveRequest(Long id);
	void disapproveRequest(Long id);
	AgentRegistrationRequest findById(Long id);
	Collection<AgentRegistrationRequest> findAllByUsername(String username);
	void deleteRequest(Long id);
}
