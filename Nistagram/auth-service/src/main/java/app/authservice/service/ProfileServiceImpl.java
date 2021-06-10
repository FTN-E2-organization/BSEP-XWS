package app.authservice.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.authservice.dto.*;
import app.authservice.email.EmailService;
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
	
	@Autowired
	public ProfileServiceImpl(ProfileRepository profileRepository, AuthorityRepository authorityRepository, ApplicationEventPublisher publisher,
			PasswordEncoder passwordEncoder, ConfirmationTokenRepository confirmationTokenRepository, EmailService emailService, RecoveryTokenRepository recoveryTokenRepository, UserRepository userRepository) {
		this.publisher = publisher;
		this.profileRepository = profileRepository;
		this.authorityRepository = authorityRepository;
		this.passwordEncoder = passwordEncoder;
		this.recoveryTokenRepository = recoveryTokenRepository;
		this.userRepository = userRepository;
		this.emailService = emailService;
		this.confirmationTokenRepository = confirmationTokenRepository;	
	}

	@Override
	@Transactional
	public void createRegularUser(ProfileDTO profileDTO) throws Exception {
		if(profileRepository.existsByUsername(profileDTO.username))
			throw new BadRequest("Username is busy.");
		if(!checkPassword(profileDTO.password)) {
			throw new BadRequest("Password is too weak and is currently blacklisted.");
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
		profile.setDeleted(profileDTO.isDeleted);
		profile.setVerified(profileDTO.isVerified);
		profile.setAllowedUnfollowerMessages(profileDTO.allowedUnfollowerMessages);
		profile.setAllowedTagging(profileDTO.allowedTagging);
		profile.setStatus(ProfileStatus.created);
		profile.setAuthorities(authorities);
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

	@Override
	public void addAgentRoleToRegularUser(String username) {
		/*Provjeriti ovu metodu, radjena je za potrebe BSEP*/
		Profile profile = profileRepository.findByUsername(username);
		
		Set<Authority> authorities = new HashSet<Authority>();
		authorities.add(authorityRepository.findByName("ROLE_REGULAR"));
		authorities.add(authorityRepository.findByName("ROLE_AGENT"));
		
		profile.setVerified(true);
		profile.setAuthorities(authorities);
		
		profileRepository.save(profile);
	}

	@Override
	public void confirmProfile(String confirmationToken) throws Exception {
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);	
		Profile profile = profileRepository.findByUsername(token.getProfile().getUsername());
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
		User user = profileRepository.findByUsername(username);
		if (user == null || !user.isEnabled()) { 
			return false;
		}
		RecoveryToken token = recoveryTokenRepository.findByUser(user);
		if(token == null) {
			token = new RecoveryToken();
			token.setUser(user);
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
		user.setPassword(passwordEncoder.encode(dto.password));
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}
}
