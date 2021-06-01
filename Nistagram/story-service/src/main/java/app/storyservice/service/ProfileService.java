package app.storyservice.service;

import java.util.Collection;

import app.storyservice.dto.ProfileDTO;
import app.storyservice.model.Profile;

public interface ProfileService {
	Collection<Profile> getAllProfiles();

	void create(ProfileDTO profileDTO);
}
