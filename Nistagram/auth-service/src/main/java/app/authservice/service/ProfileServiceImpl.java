package app.authservice.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.authservice.dto.*;
import app.authservice.enums.ProfileStatus;
import app.authservice.event.ProfileEvent;
import app.authservice.event.ProfileEventType;
import app.authservice.exception.BadRequest;
import app.authservice.model.*;
import app.authservice.repository.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ProfileServiceImpl implements ProfileService {

	private ProfileRepository profileRepository;
	private RoleRepository roleRepository;
	private final ApplicationEventPublisher publisher;
	private EmailService emailService;
	private ConfirmationTokenRepository confirmationTokenRepository;
	
	@Autowired
	public ProfileServiceImpl(ProfileRepository profileRepository, RoleRepository roleRepository, ApplicationEventPublisher publisher,
							  ConfirmationTokenRepository confirmationTokenRepository, EmailService emailService) {
		this.publisher = publisher;
		this.profileRepository = profileRepository;
		this.roleRepository = roleRepository;
		this.emailService = emailService;
		this.confirmationTokenRepository = confirmationTokenRepository;		
	}

	@Override
	@Transactional
	public void createRegularUser(ProfileDTO profileDTO) throws Exception {
		if(profileRepository.existsByUsername(profileDTO.username))
			throw new BadRequest("Username is busy.");
		
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
		profile.setDeleted(profileDTO.isDeleted);
		profile.setVerified(profileDTO.isVerified);
		profile.setAllowedUnfollowerMessages(profileDTO.allowedUnfollowerMessages);
		profile.setAllowedTagging(profileDTO.allowedTagging);
		profile.setStatus(ProfileStatus.created);
		profile.setRoles(roles);		
		profile.setEnabled(false);
				
		profileRepository.save(profile);
		publishProfileCreated(profile);
		
		ConfirmationToken confirmationToken = new ConfirmationToken(profile);
		confirmationTokenRepository.save(confirmationToken);
		emailService.sendActivationEmail(profile.getEmail(), confirmationToken);		
	}
	
	private void publishProfileCreated(Profile profile) {
        ProfileEvent event = new ProfileEvent(UUID.randomUUID().toString(),profile.getUsername(), profile, ProfileEventType.create);        
        publisher.publishEvent(event);
    }

	@Override
	@Transactional
	public void updatePersonalData(String oldUsername, ProfileDTO profileDTO) throws Exception {	
		System.out.println("Old username: " + oldUsername);
		System.out.println("New username: " + profileDTO.username);
		Profile profile = profileRepository.findByUsername(oldUsername);
		
		if(!oldUsername.equals(profileDTO.username)) {
			if(profileRepository.existsByUsername(profileDTO.username))
				throw new BadRequest("Username is busy.");
			profile.setUsername(profileDTO.username);
		}
		
		profile.setName(profileDTO.name);
		profile.setEmail(profileDTO.email);
		profile.setDateOfBirth(profileDTO.dateOfBirth);
		profile.setGender(profileDTO.gender);
		profile.setBiography(profileDTO.biography);
		profile.setPhone(profileDTO.phone);
		profile.setWebsite(profileDTO.website);
		
		profileRepository.save(profile);
		publishProfileUpdatedPesonalData(oldUsername, profile);
	}
	
	private void publishProfileUpdatedPesonalData(String oldUsername, Profile profile) {
		ProfileEvent event = new ProfileEvent(UUID.randomUUID().toString(), oldUsername, profile, ProfileEventType.updatePersonalData);        
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

	@Override
	public ProfileDTO getProfileByUsername(String username) {
		Profile profile = profileRepository.findByUsername(username);
		ProfileDTO profileDTO = new ProfileDTO(profile.getUsername(), profile.getEmail(), profile.getPassword(), profile.getName(), profile.getDateOfBirth(), profile.getGender(), profile.getBiography(), profile.getPhone(), profile.getWebsite(), profile.isPublic(), profile.isVerified(), profile.isAllowedUnfollowerMessages(), profile.isAllowedTagging(),false);

		return profileDTO;
	}

	@Override
	public Collection<ProfileDTO> getPublicProfiles() {
		Collection<ProfileDTO> profileDTOs = new ArrayList<>();
		Collection<Profile> profiles = profileRepository.findAllPublic();
		for (Profile profile : profiles) {
			ProfileDTO profileDTO = new ProfileDTO(profile.getUsername(), profile.getEmail(), profile.getPassword(), profile.getName(), profile.getDateOfBirth(), profile.getGender(), profile.getBiography(), profile.getPhone(), profile.getWebsite(), profile.isPublic(), profile.isVerified(), profile.isAllowedUnfollowerMessages(), profile.isAllowedTagging(),false);
			profileDTOs.add(profileDTO);
		}
		return profileDTOs;
	}
	
	public List<String> findAllowTaggingProfileUsernames() {
		List<Profile> profiles = profileRepository.findAllowTaggingProfiles();
		List<String> result = new ArrayList<>();
		
		for(Profile profile:profiles) {
			result.add(profile.getUsername());
		}
		
		return result;
	}
	
}
