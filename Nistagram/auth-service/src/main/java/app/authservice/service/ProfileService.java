package app.authservice.service;

import java.util.Collection;
import java.util.List;

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
	void confirmProfile(String confirmationToken) throws Exception;

}
