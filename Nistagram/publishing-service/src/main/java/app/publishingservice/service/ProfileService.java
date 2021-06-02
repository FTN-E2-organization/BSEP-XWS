package app.publishingservice.service;

import app.publishingservice.dto.ProfileDTO;

public interface ProfileService {

	void create(ProfileDTO profileDTO);
	void update(String oldUsername, ProfileDTO profileDTO);
	boolean existsByUsername(String username);
}
