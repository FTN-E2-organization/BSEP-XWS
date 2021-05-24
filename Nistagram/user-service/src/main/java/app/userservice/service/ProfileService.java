package app.userservice.service;

import app.userservice.dto.*;
import app.userservice.model.*;

public interface ProfileService {

	void createRegularUser(ProfileDTO profileDTO);
	void updateRegularUser(ProfileDTO profileDTO);
}
