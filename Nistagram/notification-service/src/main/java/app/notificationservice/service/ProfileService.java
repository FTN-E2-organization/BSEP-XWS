package app.notificationservice.service;

import app.notificationservice.dto.ProfileDTO;

public interface ProfileService {

	void create(ProfileDTO profileDTO) throws Exception;
	void updatePersonalData(String oldUsername, ProfileDTO profileDTO);
	void blockProfile(String username);
	ProfileDTO findByUsername(String username);
}
