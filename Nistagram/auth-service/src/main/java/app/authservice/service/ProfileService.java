package app.authservice.service;

import java.util.Collection;
import java.util.List;

import org.springframework.mail.MailException;

import app.authservice.dto.*;

public interface ProfileService {

	void createRegularUser(ProfileDTO profileDTO) throws Exception;
	void updatePersonalData(String oldUsername, ProfileDTO profileDTO) throws Exception;
	void cancel(String username);
	void done(String username);
	ProfileDTO getProfileByUsername(String username);
	Collection<ProfileDTO> getPublicProfiles();
	List<String> findAllowTaggingProfileUsernames();
	void addAgentRoleToRegularUser(String username);
	boolean recoverPassword(String username) throws MailException, InterruptedException;
	boolean changePassword(PasswordRequestDTO dto) throws Exception;
	void confirmProfile(String confirmationToken) throws Exception;
	void sendNewActivationLink(String username) throws Exception;
	void setPassword(PasswordDTO dto) throws Exception;

}
