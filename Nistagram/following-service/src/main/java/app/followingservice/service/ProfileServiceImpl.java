package app.followingservice.service;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.followingservice.dto.ProfileDTO;
import app.followingservice.model.Profile;
import app.followingservice.repository.ProfileRepository;


@Service
public class ProfileServiceImpl implements ProfileService{
	
	private ProfileRepository profileRepository;

	@Autowired
	public ProfileServiceImpl(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}
	
	@Override
	public Collection<Profile> getAllProfiles() {
		return profileRepository.getAllProfiles();
	}

	@Override
	public Collection<Profile> getFollowingByUsername(String username) {
		return profileRepository.getFollowing(username);
	}

	@Override
	public Collection<Profile> getFollowersByUsername(String username) {
		return profileRepository.getFollowers(username);
	}

	@Override
	public void createNewFriendship(String startNodeUsername, String endNodeUsername) {
		profileRepository.createNewFriendship(startNodeUsername, endNodeUsername);
	}

	@Override
	public void deleteFriendship(String startNodeUsername, String endNodeUsername) {
		profileRepository.deleteFriendship(startNodeUsername, endNodeUsername);
	}

	@Override
	public void setMuted(String startNodeUsername, String endNodeUsername, boolean isMuted) {
		profileRepository.setMuted(startNodeUsername, endNodeUsername, isMuted);
	}

	@Override
	public void setClose(String startNodeUsername, String endNodeUsername, boolean isClose) {
		profileRepository.setClose(startNodeUsername, endNodeUsername, isClose);
	}

	@Override
	public void setActivePostNotification(String startNodeUsername, String endNodeUsername,
			boolean isActivePostNotification) {
		profileRepository.setActivePostNotification(startNodeUsername, endNodeUsername, isActivePostNotification);
	}

	@Override
	public void setActiveStoryNotification(String startNodeUsername, String endNodeUsername,
			boolean isActiveStoryNotification) {
		profileRepository.setActiveStoryNotification(startNodeUsername, endNodeUsername, isActiveStoryNotification);
	}

	@Override
	@Transactional
	public void addProfile(ProfileDTO profileDTO) {
		Profile profile = new Profile();
		
		profile.setUsername(profileDTO.username);
		profile.setPublic(profileDTO.isPublic);
		
		profileRepository.save(profile);
	}

	@Override
	public void deleteProfile(String username) {
		profileRepository.deleteProfile(username);
	}

	@Override
	public Collection<Profile> getProfilesByCategoryName(String categoryName) {
		return profileRepository.getProfilesByCategoryName(categoryName);
	}

	@Override
	public void createFollowRequest(String startNodeUsername, String endNodeUsername) {
		profileRepository.createFollowRequest(startNodeUsername, endNodeUsername);
	}

	@Override
	public void deleteFollowRequest(String startNodeUsername, String endNodeUsername) {
		profileRepository.deleteFollowRequest(startNodeUsername, endNodeUsername);
	}

	@Override
	public Collection<Profile> getSendRequests(String username) {
		return profileRepository.getSendRequests(username);
	}

	@Override
	public Collection<Profile> getReceivedRequests(String username) {
		return profileRepository.getReceivedRequests(username);
	}

	@Override
	public LocalDateTime getTimeStampOfRequest(String startNodeUsername, String endNodeUsername) {
		return profileRepository.getTimeStampOfRequest(startNodeUsername, endNodeUsername);
	}

	@Override
	@Transactional
	public void updateProfile(String oldUsername, ProfileDTO profileDTO) {
		Profile profile = profileRepository.getProfileByUsername(oldUsername);
		
		profile.setUsername(profileDTO.username);
		profile.setPublic(profileDTO.isPublic);
		
		profileRepository.save(profile);
	}

	@Override
	public Profile getProfileByUsername(String username) {
		return profileRepository.getProfileByUsername(username);
	}

}
