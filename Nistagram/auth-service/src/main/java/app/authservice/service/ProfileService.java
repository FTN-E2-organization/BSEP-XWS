package app.authservice.service;

import java.util.Collection;
import java.util.List;

import org.springframework.mail.MailException;

import app.authservice.dto.*;
import app.authservice.model.Category;
import app.authservice.model.ProfileVerification;

public interface ProfileService {

	void createRegularUser(ProfileDTO profileDTO) throws Exception;
	void updatePersonalData(String oldUsername, ProfileDTO profileDTO) throws Exception;
	void updateProfilePrivacy(ProfileDTO profileDTO);
	void blockProfile(String username);
	void cancel(String username);
	void done(String username);
	ProfileDTO getProfileByUsername(String username);
	Collection<ProfileDTO> getPublicProfiles();
	List<String> findAllowTaggingProfileUsernames();
	void addAgentRoleToRegularUser(AgentRegistrationRequestDTO requestDTO);
	boolean recoverPassword(String username) throws MailException, InterruptedException;
	boolean changePassword(PasswordRequestDTO dto) throws Exception;
	void confirmProfile(String confirmationToken) throws Exception;
	void sendNewActivationLink(String username) throws Exception;
	void setPassword(PasswordDTO dto) throws Exception;
	Long createVerificationRequest(VerificationRequestDTO requestDTO) throws Exception;
	List<CategoryDTO> getCategories();
	Collection<VerificationRequestDTO> getUnverifiedProfiles();
	void judgeVerificationRequest(VerificationRequestDTO requestDTO);
	CategoryDTO getCategory(String username);
	boolean checkExistRequest(String username);
	
}
