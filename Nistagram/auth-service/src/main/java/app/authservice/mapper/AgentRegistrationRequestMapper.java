package app.authservice.mapper;

import java.util.ArrayList;
import java.util.Collection;

import app.authservice.dto.AgentRegistrationRequestDTO;
import app.authservice.model.AgentRegistrationRequest;

public class AgentRegistrationRequestMapper {

	public static Collection<AgentRegistrationRequestDTO> toRequestDTOs (Collection<AgentRegistrationRequest> requests){
		Collection<AgentRegistrationRequestDTO> requestDTOs = new ArrayList<>();
		
		for(AgentRegistrationRequest request:requests) {
			requestDTOs.add(new AgentRegistrationRequestDTO(request.getId(), request.getProfile().getUsername(), request.getIsApproved(), 
					request.getEmail(), request.getWebSite(), request.getTimestamp()));
		}
		
		return requestDTOs;
	}
	
	public static AgentRegistrationRequestDTO toDto (AgentRegistrationRequest request) {
		return new AgentRegistrationRequestDTO(request.getId(), request.getProfile().getUsername(), request.getIsApproved(), 
				request.getEmail(), request.getWebSite(), request.getTimestamp());
	}
}
