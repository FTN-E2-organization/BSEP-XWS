package app.authservice.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.authservice.dto.*;

import app.authservice.enums.ProfileStatus;
import app.authservice.event.ProfileEvent;
import app.authservice.event.ProfileEventType;
import app.authservice.exception.BadRequest;
import app.authservice.model.*;
import app.authservice.repository.*;
import app.authservice.validator.ProfileValidator;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ProfileServiceImpl implements ProfileService {

	private ProfileRepository profileRepository;
	private AuthorityRepository authorityRepository;
	private final ApplicationEventPublisher publisher;
	private PasswordEncoder passwordEncoder;
	private EmailService emailService;
	private RecoveryTokenRepository recoveryTokenRepository;
	private UserRepository userRepository;
	private ConfirmationTokenRepository confirmationTokenRepository;
	private static Logger log = LoggerFactory.getLogger(ProfileServiceImpl.class);
	private VerificationRequestRepository verificationRequestRepository;
	private CategoryRepository categoryRepository;
	
	@Autowired
	public ProfileServiceImpl(ProfileRepository profileRepository, AuthorityRepository authorityRepository, ApplicationEventPublisher publisher,
			PasswordEncoder passwordEncoder, ConfirmationTokenRepository confirmationTokenRepository, EmailService emailService, RecoveryTokenRepository recoveryTokenRepository, UserRepository userRepository,
			VerificationRequestRepository verificationRequestRepository, CategoryRepository categoryRepository) {
		this.publisher = publisher;
		this.profileRepository = profileRepository;
		this.authorityRepository = authorityRepository;
		this.passwordEncoder = passwordEncoder;
		this.recoveryTokenRepository = recoveryTokenRepository;
		this.userRepository = userRepository;
		this.emailService = emailService;
		this.confirmationTokenRepository = confirmationTokenRepository;	
		this.verificationRequestRepository = verificationRequestRepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	@Transactional
	public void createRegularUser(ProfileDTO profileDTO) throws Exception {
		
		if(profileRepository.existsByUsername(profileDTO.username))
			throw new BadRequest("Username is busy.");
		if(!checkPassword(profileDTO.password)) {
			throw new BadRequest("Password is too weak and is currently blacklisted.");
		}
		try {
			log.info("User attempted registration ");
		} catch (Exception e) {
		}
		Profile profile = new Profile();
		Set<Authority> authorities = new HashSet<Authority>();
		authorities.add(authorityRepository.findByName("ROLE_REGULAR"));
		
		profile.setUsername(profileDTO.username);
		profile.setName(profileDTO.name);
		profile.setEmail(profileDTO.email);
		String salt = generateSalt();
		profile.setSalt(salt);
		profile.setPassword(passwordEncoder.encode(profileDTO.password + salt));
		profile.setDateOfBirth(profileDTO.dateOfBirth);
		profile.setGender(profileDTO.gender);
		profile.setBiography(profileDTO.biography);
		profile.setPhone(profileDTO.phone);
		profile.setWebsite(profileDTO.website);
		profile.setPublic(profileDTO.isPublic);
		profile.setBlocked(false);
		profile.setVerified(profileDTO.isVerified);
		profile.setAllowedUnfollowerMessages(profileDTO.allowedUnfollowerMessages);
		profile.setAllowedTagging(profileDTO.allowedTagging);
		profile.setStatus(ProfileStatus.created);
		profile.setAuthorities(authorities);
		profile.setEnabled(false);
		
		profile.setAllowedAllLikes(true);
		profile.setAllowedAllComments(true);
		profile.setAllowedAllMessages(true);
				
		profileRepository.save(profile);
		
		ConfirmationToken confirmationToken = new ConfirmationToken(profile);
		confirmationTokenRepository.save(confirmationToken);
		emailService.sendActivationEmail(profile.getEmail(), confirmationToken);
		
		publishProfileCreated(new PublishProfileDTO(profileDTO.username, profileDTO.isPublic, profileDTO.isVerified, 
							 profileDTO.allowedUnfollowerMessages, profileDTO.allowedTagging, false));
	}

	private void publishProfileCreated(PublishProfileDTO profileDTO) {
		ProfileEvent event = new ProfileEvent(UUID.randomUUID().toString(),profileDTO.username, profileDTO, ProfileEventType.create);     
        publisher.publishEvent(event);
    }

	@Override
	@Transactional
	public void updatePersonalData(String oldUsername, ProfileDTO profileDTO) throws Exception {	
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
		publishProfileUpdatedPesonalData(oldUsername, new PublishProfileDTO(profileDTO.username, profileDTO.name, profileDTO.email,
				profileDTO.dateOfBirth, profileDTO.gender, profileDTO.biography, profileDTO.phone, profileDTO.website));
	}
	
	private void publishProfileUpdatedPesonalData(String oldUsername, PublishProfileDTO profileDTO) {
		ProfileEvent event = new ProfileEvent(UUID.randomUUID().toString(), oldUsername, profileDTO, ProfileEventType.updatePersonalData);        
        publisher.publishEvent(event);
    }
	
	@Override
	@Transactional
	public void updateProfilePrivacy(ProfileDTO profileDTO) {
		Profile profile = profileRepository.findByUsername(profileDTO.username);
		
		profile.setPublic(profileDTO.isPublic);
		profile.setAllowedUnfollowerMessages(profileDTO.allowedUnfollowerMessages);
		profile.setAllowedTagging(profileDTO.allowedTagging);
		profile.setAllowedAllLikes(profileDTO.allowedAllLikes);
		profile.setAllowedAllComments(profileDTO.allowedAllComments);
		profile.setAllowedAllMessages(profileDTO.allowedAllMessages);
		
		profileRepository.save(profile);
		
		publishProfileUpdatedPrivacy(new PublishProfileDTO(profileDTO.username, profileDTO.isPublic, profileDTO.allowedUnfollowerMessages, profileDTO.allowedTagging));
	}
	
	private void publishProfileUpdatedPrivacy(PublishProfileDTO profileDTO) {
		ProfileEvent event = new ProfileEvent(UUID.randomUUID().toString(), profileDTO.username, profileDTO, ProfileEventType.updateProfilePrivacy);        
        publisher.publishEvent(event);
    }
	
	@Override
	@Transactional
	public void blockProfile(String username) {
		Profile profile = profileRepository.findByUsername(username);
		profile.setBlocked(true);
		profileRepository.save(profile);
		
		publishProfileBlocked(new PublishProfileDTO(username));
	}
	
	private void publishProfileBlocked(PublishProfileDTO profileDTO) {
		ProfileEvent event = new ProfileEvent(UUID.randomUUID().toString(), profileDTO.username, profileDTO, ProfileEventType.block);        
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
		ProfileDTO profileDTO = new ProfileDTO(profile.getUsername(), profile.getEmail(), profile.getPassword(), profile.getName(), profile.getDateOfBirth(), profile.getGender(), profile.getBiography(), profile.getPhone(), profile.getWebsite(), profile.isPublic(), profile.isVerified(), profile.isAllowedUnfollowerMessages(), profile.isAllowedTagging(),profile.isBlocked(), profile.isAllowedAllLikes(), profile.isAllowedAllComments(), profile.isAllowedAllMessages());
		return profileDTO;
	}

	@Override
	public Collection<ProfileDTO> getPublicProfiles() {
		Collection<ProfileDTO> profileDTOs = new ArrayList<>();
		Collection<Profile> profiles = profileRepository.findAllPublic();
		for (Profile profile : profiles) {
			ProfileDTO profileDTO = new ProfileDTO(profile.getUsername(), profile.getEmail(), profile.getPassword(), profile.getName(), profile.getDateOfBirth(), profile.getGender(), profile.getBiography(), profile.getPhone(), profile.getWebsite(), profile.isPublic(), profile.isVerified(), profile.isAllowedUnfollowerMessages(), profile.isAllowedTagging(),profile.isBlocked(), profile.isAllowedAllLikes(), profile.isAllowedAllComments(), profile.isAllowedAllMessages());
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

	@Override
	public void addAgentRoleToRegularUser(AgentRegistrationRequestDTO requestDTO) {
		Profile profile = profileRepository.findByUsername(requestDTO.username);
		
		Set<Authority> authorities = new HashSet<Authority>();
		authorities.add(authorityRepository.findByName("ROLE_REGULAR"));
		authorities.add(authorityRepository.findByName("ROLE_AGENT"));
		
		profile.setVerified(true);
		profile.setAuthorities(authorities);
		profile.setEmail(requestDTO.email);
		profile.setWebsite(requestDTO.webSite);
		
		profileRepository.save(profile);
	}

	@Override
	public void confirmProfile(String confirmationToken) throws Exception {
		
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);	
		Profile profile = profileRepository.findByUsername(token.getProfile().getUsername());
		try {
			log.info("User profile confirmation: " + profile.getId());
		} catch (Exception e) {
		}
		if(token != null && (token.getCreationDate().plusDays((long) 1).isAfter(LocalDateTime.now()))) {						
			profile.setEnabled(true);
			profileRepository.save(profile);
			emailService.sendInformationEmail(profile.getEmail(), "Successful account activation");
			return;
		}		
		emailService.sendInformationEmail(profile.getEmail(), "Unsuccessful account activation");
	}

	@Override
	public void sendNewActivationLink(String username) throws Exception {
		ConfirmationToken oldToken = confirmationTokenRepository.getTokenByUsername(username);
		Profile profile = profileRepository.findByUsername(username);
		try {
			log.info("User new activation attempt: " + profile.getId());
		} catch (Exception e) {
		}
		if (oldToken == null) {
			throw new Exception("You did not register!");
		}
		else if (oldToken.getProfile().isEnabled()) {
			throw new Exception("Your account is already active!");
		}
		else if (oldToken.getCreationDate().plusDays((long) 1).isAfter(LocalDateTime.now())) {
			throw new Exception("Your old activation link is still valid!");
		}
		
		ConfirmationToken newToken = new ConfirmationToken(oldToken.getProfile());
		newToken.setTokenid(oldToken.getTokenid());
		confirmationTokenRepository.save(newToken);
		emailService.sendActivationEmail(oldToken.getProfile().getEmail(), newToken);
	}
	
	
	
	private String generateSalt() {
		String salt = UUID.randomUUID().toString().substring(0, 8);
		return salt;
	}	
	
	@Override
	public boolean recoverPassword(String username) throws MailException, InterruptedException {
		Profile profile = profileRepository.findByEmail(username); 
		try {
			log.info("User password recovery: " + profile.getId());
		} catch (Exception e) {
		}
		if (profile == null || !profile.isEnabled()) { 
			return false;
		}
		RecoveryToken token = recoveryTokenRepository.findByUser(profile);
		if(token == null) {
			token = new RecoveryToken();
			token.setUser(profile);
		}
		token.setExparationTime(LocalDateTime.now().plusMinutes(10));
		token.setRecoveryToken(UUID.randomUUID().toString());
		recoveryTokenRepository.save(token);
		emailService.sendRecoveryEmail(username, token);

		return true;
	}

	@Override
	public boolean changePassword(PasswordRequestDTO dto) throws Exception {
		RecoveryToken token = recoveryTokenRepository.findByRecoveryToken(dto.token);
		if(token == null || token.getExparationTime().isBefore(LocalDateTime.now())) {
			return false;
		}
		if(!checkPassword(dto.password)) {
			return false;
		}
		ProfileValidator.checkPasswordFormat(dto.password);
		User user = token.getUser();
		try {
			log.info("User change password: " + user.getId());
		} catch (Exception e) {
		}
		String salt = generateSalt();	
		user.setSalt(salt);
		user.setPassword(passwordEncoder.encode(dto.password + salt));
		userRepository.save(user);
		recoveryTokenRepository.delete(token);
		return true;
	}
	
	private boolean checkPassword(String password) {
		String p = password.toLowerCase();

		try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/1000-most-common-passwords.txt"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       if(p.equals(line)) {
		    	   return false;
		       }
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public void setPassword(PasswordDTO dto) throws Exception {
		Profile profile = profileRepository.findByUsername(dto.username);		
		if (passwordEncoder.matches(dto.oldPassword + profile.getSalt(), profile.getPassword())) {
			if(!checkPassword(dto.newPassword)) {
				throw new BadRequest("Password is too weak and is currently blacklisted.");
			}
			String salt = generateSalt();	
			profile.setSalt(salt);
			profile.setPassword(passwordEncoder.encode(dto.newPassword + salt));
			userRepository.save(profile);
		} 
		else {
			throw new BadRequest("Wrong old password!");			
		}		
	}

	@Override
	public Long createVerificationRequest(VerificationRequestDTO requestDTO) throws Exception {
		
		ProfileVerification verification = new ProfileVerification();
		
		verification.setName(requestDTO.name);
		verification.setSurname(requestDTO.surname);
		verification.setProfile(profileRepository.findByUsername(requestDTO.username));
		verification.setCategory(categoryRepository.findOneByName(requestDTO.category));
				
		verificationRequestRepository.save(verification);
		return verification.getId();
	}

	@Override
	public List<CategoryDTO> getCategories() {
		List<Category> categories = categoryRepository.findAll();
		List<CategoryDTO> dtos = new ArrayList<CategoryDTO>();
		for(Category c : categories) {
			CategoryDTO dto = new CategoryDTO();
			dto.id = c.getId();
			dto.name = c.getName();
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public Collection<VerificationRequestDTO> getUnverifiedProfiles() {
		Collection<VerificationRequestDTO> profileDTOs = new ArrayList<>();
		Collection<ProfileVerification> requests = verificationRequestRepository.findAll();
		for (ProfileVerification r : requests) {
			if(r.getIsApproved()==null) {
				VerificationRequestDTO dto = new VerificationRequestDTO();
				dto.category = r.getCategory().getName();
				dto.surname = r.getSurname();
				dto.name = r.getName();
				dto.username = r.getProfile().getUsername();
				dto.id = r.getId();
				profileDTOs.add(dto);
			}
		}
		return profileDTOs;
	}

	@Override
	public void judgeVerificationRequest(VerificationRequestDTO requestDTO) {
		ProfileVerification profileVerification = verificationRequestRepository.getOne(requestDTO.id);
		profileVerification.setIsApproved(requestDTO.isApproved);
		verificationRequestRepository.save(profileVerification);
		Profile profile = profileRepository.findByUsername(requestDTO.username);
		profile.setVerified(requestDTO.isApproved);
		profileRepository.save(profile);
	}

	@Override
	public Collection<ProfileDTO> getPublicAndPrivateProfiles() {
		Collection<ProfileDTO> profileDTOs = new ArrayList<>();
		Collection<Profile> profiles = profileRepository.findAllPublicAndPrivate();
		for (Profile profile : profiles) {
			ProfileDTO profileDTO = new ProfileDTO(profile.getUsername(), profile.getEmail(), profile.getPassword(), profile.getName(), profile.getDateOfBirth(), profile.getGender(), profile.getBiography(), profile.getPhone(), profile.getWebsite(), profile.isPublic(), profile.isVerified(), profile.isAllowedUnfollowerMessages(), profile.isAllowedTagging(),profile.isBlocked(), profile.isAllowedAllLikes(), profile.isAllowedAllComments(), profile.isAllowedAllMessages());
			profileDTOs.add(profileDTO);
		}
		return profileDTOs;
	}
	public CategoryDTO getCategory(String username) {
		ProfileVerification profileVerification = verificationRequestRepository.findByProfileUsername(username);
		CategoryDTO dto = new CategoryDTO();
		dto.id = profileVerification.category.getId();
		dto.name = profileVerification.category.getName(); 
		return dto;
	}

	@Override
	public boolean checkExistRequest(String username) {
		ProfileVerification profileVerification = verificationRequestRepository.findByProfileUsername(username);
		if(profileVerification != null) {
			return true;
		}else {
			return false;
		}	
	}
	
}

