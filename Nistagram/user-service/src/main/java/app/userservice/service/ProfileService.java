package app.userservice.service;

import app.userservice.dto.AddProfileDTO;
import app.userservice.model.Profile;

public interface ProfileService {

	void addRegularUser(AddProfileDTO profileDTO);
}
