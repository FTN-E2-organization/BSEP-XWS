package app.activityservice.service;

import app.activityservice.dto.ProfileDTO;

public interface ProfileService {

	void create(ProfileDTO profileDTO);
	void updatePersonalData(String oldUsername, ProfileDTO profileDTO);
}
