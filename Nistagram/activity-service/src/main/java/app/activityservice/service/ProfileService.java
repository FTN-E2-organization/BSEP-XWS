package app.activityservice.service;

import app.activityservice.dto.ProfileDTO;

public interface ProfileService {

	void create(ProfileDTO profileDTO);
	void updatePersonalData(String oldUsername, ProfileDTO profileDTO);
	void updateProfilePrivacy(ProfileDTO profileDTO);
	void blockProfile(String username);
	ProfileDTO findByUsername(String username);
}
