package app.authservice.service;

import app.authservice.dto.*;

public interface ProfileService {

	void createRegularUser(ProfileDTO profileDTO);
	void update(ProfileDTO profileDTO);
}