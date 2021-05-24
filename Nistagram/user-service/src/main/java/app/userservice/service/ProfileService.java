package app.userservice.service;

import app.userservice.dto.AddProfileDTO;
import app.userservice.model.Profile;

public interface ProfileService {

	public Profile addRegularUser(AddProfileDTO profileDTO);
}
