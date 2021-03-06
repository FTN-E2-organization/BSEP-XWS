package app.authservice.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.authservice.dto.AgentRegistrationRequestDTO;
import app.authservice.exception.BadRequest;
import app.authservice.model.AgentRegistrationRequest;
import app.authservice.model.Profile;
import app.authservice.repository.AgentRegistrationRepository;
import app.authservice.repository.ProfileRepository;

@Service
public class AgentRegistrationServiceImpl implements AgentRegistrationService {

	private AgentRegistrationRepository agentRegistrationRepository;
	private ProfileRepository profileRepository;
	
	@Autowired
	public AgentRegistrationServiceImpl(AgentRegistrationRepository agentRegistrationRepository, ProfileRepository profileRepository) {
		this.agentRegistrationRepository = agentRegistrationRepository;
		this.profileRepository = profileRepository;
	}

	@Override
	public void create(AgentRegistrationRequestDTO requestDTO) throws Exception{
		AgentRegistrationRequest registrationRequest = new AgentRegistrationRequest();
		Profile profile = profileRepository.findByUsername(requestDTO.username);
		
		if(profile == null || (profile != null && profile.isBlocked()))
			throw new BadRequest("The selected user does not exist or is blocked.");
		
		registrationRequest.setProfile(profile);
		registrationRequest.setIsApproved(false);
		registrationRequest.setEmail(requestDTO.email);
		registrationRequest.setWebSite(requestDTO.webSite);
		registrationRequest.setIsDeleted(false);
		
		agentRegistrationRepository.save(registrationRequest);
	}

	@Override
	public Collection<AgentRegistrationRequest> getAllDisapproved() {
		return agentRegistrationRepository.findAllDisapproved();
	}

	@Override
	public void approveRequest(Long id) {
		AgentRegistrationRequest registrationRequest = agentRegistrationRepository.findById(id).get();
		registrationRequest.setIsApproved(true);
		agentRegistrationRepository.save(registrationRequest);
	}

	@Override
	public void disapproveRequest(Long id) {
		AgentRegistrationRequest registrationRequest = agentRegistrationRepository.findById(id).get();
		registrationRequest.setIsApproved(false);
		agentRegistrationRepository.save(registrationRequest);
	}

	@Override
	public AgentRegistrationRequest findById(Long id) {
		return agentRegistrationRepository.findById(id).get();
	}

	@Override
	public Collection<AgentRegistrationRequest> findAllByUsername(String username) {
		return agentRegistrationRepository.findAllByUsername(username);
	}

	@Override
	public void deleteRequest(Long id) {
		AgentRegistrationRequest registrationRequest = agentRegistrationRepository.findById(id).get();
		if(!registrationRequest.getIsApproved()) {
			registrationRequest.setIsDeleted(true);
			agentRegistrationRepository.save(registrationRequest);
		}
	}
}
