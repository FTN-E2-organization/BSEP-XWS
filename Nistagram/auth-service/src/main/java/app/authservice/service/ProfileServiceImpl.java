package app.authservice.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.authservice.dto.*;
import app.authservice.enums.ProfileStatus;
import app.authservice.event.ProfileEvent;
import app.authservice.event.ProfileEventType;
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
		publishProfileCreated(profile);
	}
	
	private void publishProfileCreated(Profile profile) {
        ProfileEvent event = new ProfileEvent(UUID.randomUUID().toString(),profile.getUsername(), profile, ProfileEventType.created);        
        publisher.publishEvent(event);
    }

	@Override
	@Transactional
	public void update(String oldUsername, ProfileDTO profileDTO) {	
		Profile profile = profileRepository.findByUsername(oldUsername);
		
		profile.setName(profileDTO.name);
		profile.setUsername(profileDTO.username);
		profile.setPassword(profileDTO.password);
		profile.setEmail(profileDTO.email);
		profile.setDateOfBirth(profileDTO.dateOfBirth);
		profile.setGender(profileDTO.gender);
		profile.setBiography(profileDTO.biography);
		profile.setPhone(profileDTO.phone);
		profile.setWebsite(profileDTO.website);
		
		profileRepository.save(profile);
		publishProfileUpdated(oldUsername, profile);
	}
	
	private void publishProfileUpdated(String oldUsername, Profile profile) {
		ProfileEvent event = new ProfileEvent(UUID.randomUUID().toString(), oldUsername, profile, ProfileEventType.updated);        
        publisher.publishEvent(event);
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
	
}
