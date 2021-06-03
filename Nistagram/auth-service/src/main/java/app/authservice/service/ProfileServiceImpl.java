package app.authservice.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.authservice.dto.*;
import app.authservice.enums.ProfileStatus;
import app.authservice.event.ProfileCreatedEvent;
import app.authservice.model.*;
import app.authservice.repository.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ProfileServiceImpl implements ProfileService {

	private ProfileRepository profileRepository;
	private RoleRepository roleRepository;
	private final ApplicationEventPublisher publisher;
	
	@Autowired
	public ProfileServiceImpl(ProfileRepository profileRepository, RoleRepository roleRepository, ApplicationEventPublisher publisher) {
		this.publisher = publisher;
		this.profileRepository = profileRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	@Transactional
	public void createRegularUser(ProfileDTO profileDTO) {
		Profile profile = new Profile();
		Set<Role> roles = new HashSet<Role>();
		roles.add(roleRepository.findByName("ROLE_REGULAR"));
		
		profile.setUsername(profileDTO.username);
		profile.setName(profileDTO.name);
		profile.setEmail(profileDTO.email);
		profile.setPassword(profileDTO.password);
		profile.setDateOfBirth(profileDTO.dateOfBirth);
		profile.setGender(profileDTO.gender);
		profile.setBiography(profileDTO.biography);
		profile.setPhone(profileDTO.phone);
		profile.setWebsite(profileDTO.website);
		profile.setPublic(profileDTO.isPublic);
		profile.setDeleted(false);
		profile.setVerified(false);
		profile.setAllowedUnfollowerMessages(profileDTO.allowedUnfollowerMessages);
		profile.setAllowedTagging(profileDTO.allowedTagging);
		profile.setStatus(ProfileStatus.created);
		profile.setRoles(roles);
				
		profileRepository.save(profile);
		
		publish(profile);
	}
	
	private void publish(Profile profile) {
        ProfileCreatedEvent event = new ProfileCreatedEvent(UUID.randomUUID().toString(), profile);        
        publisher.publishEvent(event);
    }

	@Override
	public void update(String oldUsername, ProfileDTO profileDTO) {	
		Profile profile = profileRepository.findByUsername(oldUsername);
		
		profile.setUsername(profileDTO.username);
		profile.setEmail(profileDTO.email);
		profile.setName(profileDTO.name);
		profile.setDateOfBirth(profileDTO.dateOfBirth);
		profile.setGender(profileDTO.gender);
		profile.setBiography(profileDTO.biography);
		profile.setPhone(profileDTO.phone);
		profile.setWebsite(profileDTO.website);
		
		profileRepository.save(profile);
	}

	@Override
	@Transactional
	public void cancel(String username) {
		Profile profile = profileRepository.findByUsername(username);
		profile.setStatus(ProfileStatus.canceled);
		profileRepository.save(profile);
	}

	@Override
	@Transactional
	public void done(String username) {
		Profile profile = profileRepository.findByUsername(username);
		profile.setStatus(ProfileStatus.done);
		profileRepository.save(profile);
	}

	@Override
	public ProfileDTO getProfileByUsername(String username) {
		Profile profile = profileRepository.findByUsername(username);
		ProfileDTO profileDTO = new ProfileDTO(profile.getUsername(), profile.getEmail(), profile.getPassword(), profile.getName(), profile.getDateOfBirth(), profile.getGender(), profile.getBiography(), profile.getPhone(), profile.getWebsite(), profile.isPublic(), profile.isVerified(), profile.isAllowedUnfollowerMessages(), profile.isAllowedTagging());
		
		return profileDTO;
	}
	
}
