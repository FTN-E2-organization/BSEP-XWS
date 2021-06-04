package app.authservice.service;

import java.util.Collection;

import app.authservice.dto.*;

public interface ProfileService {

	void createRegularUser(ProfileDTO profileDTO);
	void updatePersonalData(String oldUsername, ProfileDTO profileDTO);
	void cancel(String username);
	void done(String username);
	ProfileDTO getProfileByUsername(String username);
	Collection<ProfileDTO> getProfiles();
}
