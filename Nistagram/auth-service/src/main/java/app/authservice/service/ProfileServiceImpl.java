package app.authservice.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.authservice.dto.*;
import app.authservice.enums.ProfileStatus;
import app.authservice.event.ProfileCreatedEvent;
import app.authservice.model.*;
import app.authservice.repository.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
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
		profile.setEmail(profileDTO.email);
		profile.setPassword(profileDTO.password);
		profile.setName(profileDTO.name);
		profile.setDateOfBirth(profileDTO.dateOfBirth);
		profile.setGender(profileDTO.gender);
		profile.setBiography(profileDTO.biography);
		profile.setPhone(profileDTO.phone);
		profile.setWebsite(profileDTO.website);
		profile.setPublic(profileDTO.isPublic);
		profile.setRoles(roles);
		profile.setStatus(ProfileStatus.created);
		
		log.debug("Saving a profile {}", profile);
		
		profileRepository.save(profile);
		
		publish(profile);
	}
	
	private void publish(Profile profile) {
    	
        ProfileCreatedEvent event = new ProfileCreatedEvent(UUID.randomUUID().toString(), profile);
        
        log.debug("Publishing a profile created event {}", event);
        
        publisher.publishEvent(event);
    }

	@Override
	public void update(ProfileDTO profileDTO) {
		/*U kontroleru je potrebno izvuci id od trenutno ulogovanog korisnika*/
		Profile profile = profileRepository.findById((long) 2).get();
		
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
	
}
