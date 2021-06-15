package app.followingservice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
	public Collection<ProfileDTO> getAllProfiles() {
		Collection<Profile> profiles = profileRepository.getAllProfiles();
		Collection<ProfileDTO> profileDTOs = new ArrayList<>();
		for(Profile p: profiles) {
			profileDTOs.add(new ProfileDTO(p.getUsername(), p.isPublic()));
		}
		return profileDTOs;
	}

	@Override
	public Collection<ProfileDTO> getFollowingByUsername(String username) {
		Collection<Profile> profiles = profileRepository.getFollowing(username);
		Collection<ProfileDTO> profileDTOs = new ArrayList<>();
		for(Profile p: profiles) {
			profileDTOs.add(new ProfileDTO(p.getUsername(), p.isPublic()));
		}
		return profileDTOs;
	}

	@Override
	public Collection<ProfileDTO> getFollowersByUsername(String username) {
		Collection<Profile> profiles = profileRepository.getFollowers(username);
		Collection<ProfileDTO> profileDTOs = new ArrayList<>();
		for(Profile p: profiles) {
			profileDTOs.add(new ProfileDTO(p.getUsername(), p.isPublic()));
		}
		return profileDTOs;
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
	public Collection<ProfileDTO> getProfilesByCategoryName(String categoryName) {
		Collection<Profile> profiles = profileRepository.getProfilesByCategoryName(categoryName);
		Collection<ProfileDTO> profileDTOs = new ArrayList<>();
		for(Profile p: profiles) {
			profileDTOs.add(new ProfileDTO(p.getUsername(), p.isPublic()));
		}
		return profileDTOs;
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
	public Collection<ProfileDTO> getSendRequests(String username) {
		Collection<Profile> profiles = profileRepository.getSendRequests(username);
		Collection<ProfileDTO> profileDTOs = new ArrayList<>();
		for(Profile p: profiles) {
			profileDTOs.add(new ProfileDTO(p.getUsername(), p.isPublic()));
		}
		return profileDTOs;
	}

	@Override
	public Collection<ProfileDTO> getReceivedRequests(String username) {
		Collection<Profile> profiles = profileRepository.getReceivedRequests(username);
		Collection<ProfileDTO> profileDTOs = new ArrayList<>();
		for(Profile p: profiles) {
			profileDTOs.add(new ProfileDTO(p.getUsername(), p.isPublic()));
		}
		return profileDTOs;
	}

	@Override
	public LocalDateTime getTimeStampOfRequest(String startNodeUsername, String endNodeUsername) {
		return profileRepository.getTimeStampOfRequest(startNodeUsername, endNodeUsername);
	}

	@Override
	@Transactional
	public void updatePersonalData(String oldUsername, ProfileDTO profileDTO) {
		Profile profile = profileRepository.getProfileByUsername(oldUsername);
		
		profile.setUsername(profileDTO.username);
		
		profileRepository.save(profile);
	}

	@Override
	public ProfileDTO getProfileByUsername(String username) {
		Profile profile = profileRepository.getProfileByUsername(username);
		ProfileDTO profileDTO = new ProfileDTO(profile.getUsername(), profile.isPublic());
		return profileDTO;
	}

	@Override
	public boolean getClose(String startNodeUsername, String endNodeUsername) {
		return profileRepository.getClose(startNodeUsername, endNodeUsername);
	}

	@Override
	public boolean getMuted(String startNodeUsername, String endNodeUsername) {
		return profileRepository.getMuted(startNodeUsername, endNodeUsername);
	}

	@Override
	public boolean getActivePostNotification(String startNodeUsername, String endNodeUsername) {
		return profileRepository.getActivePostNotification(startNodeUsername, endNodeUsername);
	}

	@Override
	public boolean getActiveStoryNotification(String startNodeUsername, String endNodeUsername) {
		return profileRepository.getActiveStoryNotification(startNodeUsername, endNodeUsername);
	}

	@Override
	public void createBlocking(String startNodeUsername, String endNodeUsername) {
		profileRepository.createBlocking(startNodeUsername, endNodeUsername);
	}

	@Override
	public void deleteBlocking(String startNodeUsername, String endNodeUsername) {
		profileRepository.deleteBlocking(startNodeUsername, endNodeUsername);
	}

	@Override
	public Collection<ProfileDTO> getBlockedProfiles(String username) {
		Collection<Profile> profiles = profileRepository.getBlockedProfiles(username);
		Collection<ProfileDTO> profileDTOs = new ArrayList<>();
		for(Profile p: profiles) {
			profileDTOs.add(new ProfileDTO(p.getUsername(), p.isPublic()));
		}
		return profileDTOs;
	}

}
