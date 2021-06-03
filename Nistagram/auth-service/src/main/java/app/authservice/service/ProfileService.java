package app.authservice.service;

import app.authservice.dto.*;

public interface ProfileService {

	void createRegularUser(ProfileDTO profileDTO);
	void update(String oldUsername, ProfileDTO profileDTO);
	void cancel(String username);
	void done(String username);
	ProfileDTO getProfileByUsername(String username);
}
