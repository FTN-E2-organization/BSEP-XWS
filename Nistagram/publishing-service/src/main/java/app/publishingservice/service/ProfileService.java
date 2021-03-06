package app.publishingservice.service;

import app.publishingservice.dto.ProfileDTO;

public interface ProfileService {

	void create(ProfileDTO profileDTO) throws Exception;
	void updatePersonalData(String oldUsername, ProfileDTO profileDTO);
	void updateProfilePrivacy(ProfileDTO profileDTO);
	void blockProfile(String username);
	boolean existsByUsername(String username);
	ProfileDTO findByUsername(String username);
	Long getIdByUsername(String ownerUsername);
	void deleteByUsername(String username);
	ProfileDTO getOwnerOfPost(Long postId);
	ProfileDTO getOwnerOfStory(Long storyId);
}
