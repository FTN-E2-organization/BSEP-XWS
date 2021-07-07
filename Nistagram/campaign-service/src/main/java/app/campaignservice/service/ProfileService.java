package app.campaignservice.service;

import java.util.Collection;

import app.campaignservice.dto.ProfileDTO;

public interface ProfileService {

	void create(ProfileDTO profileDTO) throws Exception;
	void updatePersonalData(String oldUsername, ProfileDTO profileDTO);
	void updateProfilePrivacy(ProfileDTO profileDTO);
	void blockProfile(String username);
	boolean existsByUsername(String username);
	ProfileDTO findByUsername(String username);
	Long getIdByUsername(String ownerUsername);
	void deleteByUsername(String username);
	void createInfluencer(String username);
	Collection<ProfileDTO> findInfluencers();
}
