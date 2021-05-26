package app.userservice.service;

import app.userservice.dto.*;

public interface ProfileService {

	void createRegularUser(ProfileDTO profileDTO);
	void update(ProfileDTO profileDTO);
}
