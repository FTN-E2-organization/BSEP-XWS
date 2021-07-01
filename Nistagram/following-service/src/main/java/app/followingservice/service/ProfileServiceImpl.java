package app.followingservice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.followingservice.dto.NotificationsSettingsDTO;
import app.followingservice.dto.ProfileDTO;
import app.followingservice.exception.BadRequest;
import app.followingservice.model.Profile;
import app.followingservice.repository.ProfileRepository;
import app.followingservice.event.ProfileCanceledEvent;


@Service
public class ProfileServiceImpl implements ProfileService{
	
	private ProfileRepository profileRepository;
	private final ApplicationEventPublisher publisher;

	@Autowired
	public ProfileServiceImpl(ProfileRepository profileRepository, ApplicationEventPublisher publisher) {
		this.profileRepository = profileRepository;
		this.publisher = publisher;
	}
	
	@Override
	public Collection<ProfileDTO> getAllProfiles() {
		Collection<Profile> profiles = profileRepository.getAllProfiles();
		Collection<ProfileDTO> profileDTOs = new ArrayList<>();
		for(Profile p: profiles) {
			profileDTOs.add(new ProfileDTO(p.getUsername(), p.isPublic(), p.isBlocked()));
		}
		return profileDTOs;
	}

	@Override
	public Collection<ProfileDTO> getFollowingByUsername(String username) {
		Collection<Profile> profiles = profileRepository.getFollowing(username);
		Collection<ProfileDTO> profileDTOs = new ArrayList<>();
		for(Profile p: profiles) {
			profileDTOs.add(new ProfileDTO(p.getUsername(), p.isPublic(), p.isBlocked()));
		}
		return profileDTOs;
	}

	@Override
	public Collection<ProfileDTO> getFollowersByUsername(String username) {
		Collection<Profile> profiles = profileRepository.getFollowers(username);
		Collection<ProfileDTO> profileDTOs = new ArrayList<>();
		for(Profile p: profiles) {
			profileDTOs.add(new ProfileDTO(p.getUsername(), p.isPublic(), p.isBlocked()));
		}
		return profileDTOs;
	}

	@Override
	public void createNewFriendship(String startNodeUsername, String endNodeUsername) throws Exception{
		if(profileRepository.isFriendship(startNodeUsername, endNodeUsername)==null) {
			profileRepository.createNewFriendship(startNodeUsername, endNodeUsername);
		}else {
			throw new BadRequest("Friendship already exists.");
		}
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
	@Transactional(rollbackFor = { Exception.class })
	public void addProfile(ProfileDTO profileDTO) throws Exception{
		try {
			Profile profile = new Profile();
			
			existsByUsername(profileDTO.username);
			
			profile.setUsername(profileDTO.username);
			profile.setPublic(profileDTO.isPublic);
			profile.setBlocked(false);
			
			profileRepository.save(profile);
		}
		catch (Exception e) {
			publishProfileCanceled(profileDTO.username, "An error occurred while creating profile in following service.");
		}
	}
	
	private void publishProfileCanceled(String username, String reason) {
		ProfileCanceledEvent event = new ProfileCanceledEvent(UUID.randomUUID().toString(), username,reason);     
        publisher.publishEvent(event);
    }

	@Override
	@Transactional
	public void deleteProfile(String username) {
		profileRepository.deleteProfile(username);
	}

	@Override
	public Collection<ProfileDTO> getProfilesByCategoryName(String categoryName) {
		Collection<Profile> profiles = profileRepository.getProfilesByCategoryName(categoryName);
		Collection<ProfileDTO> profileDTOs = new ArrayList<>();
		for(Profile p: profiles) {
			profileDTOs.add(new ProfileDTO(p.getUsername(), p.isPublic(), p.isBlocked()));
		}
		return profileDTOs;
	}

	@Override
	public void createFollowRequest(String startNodeUsername, String endNodeUsername) throws Exception{
		if(profileRepository.isFollowRequest(startNodeUsername, endNodeUsername)==null) {
			profileRepository.createFollowRequest(startNodeUsername, endNodeUsername);
		}
		else {
			throw new BadRequest("Follow request already exists.");
		}
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
			profileDTOs.add(new ProfileDTO(p.getUsername(), p.isPublic(), p.isBlocked()));
		}
		return profileDTOs;
	}

	@Override
	public Collection<ProfileDTO> getReceivedRequests(String username) {
		Collection<Profile> profiles = profileRepository.getReceivedRequests(username);
		Collection<ProfileDTO> profileDTOs = new ArrayList<>();
		for(Profile p: profiles) {
			for(ProfileDTO pDTO : getBlockedProfiles(username)) {
				if(!pDTO.username.equals(p.getUsername())) {
					profileDTOs.add(new ProfileDTO(p.getUsername(), p.isPublic(), p.isBlocked()));
				}
			}
			
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
	@Transactional
	public void updateProfilePrivacy(ProfileDTO profileDTO) {
		Profile profile = profileRepository.getProfileByUsername(profileDTO.username);
		
		profile.setPublic(profileDTO.isPublic);
		
		profileRepository.save(profile);
	}

	@Override
	public ProfileDTO getProfileByUsername(String username) {
		Profile profile = profileRepository.getProfileByUsername(username);
		ProfileDTO profileDTO = new ProfileDTO(profile.getUsername(), profile.isPublic(), profile.isBlocked());
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
			profileDTOs.add(new ProfileDTO(p.getUsername(), p.isPublic(), p.isBlocked()));
		}
		return profileDTOs;
	}

	@Override
	public void existsByUsername(String username) throws Exception{
		Collection<Profile> profiles = profileRepository.getAllProfiles();
		for(Profile p: profiles) {
			if(p.getUsername().equals(username)) {
				throw new Exception();
			}
		}
	}

	@Override
	public Collection<ProfileDTO> getUnmuteFollowingByUsername(String username) {
		Collection<ProfileDTO> profiles = getFollowingByUsername(username);
		Collection<ProfileDTO> unmuteProfiles = new ArrayList<>();
		for(ProfileDTO p : profiles) {
			if(!getMuted(username, p.username)) {
				unmuteProfiles.add(p);
			}
		}
		return unmuteProfiles;
	}

	@Override
	public boolean getActiveLikesNotification(String startNodeUsername, String endNodeUsername) {
		return profileRepository.getActiveLikesNotification(startNodeUsername, endNodeUsername);
	}

	@Override
	public boolean getActiveCommentsNotification(String startNodeUsername, String endNodeUsername) {
		return profileRepository.getActiveCommentsNotification(startNodeUsername, endNodeUsername);
	}
	
	@Override
	@Transactional
	public void blockProfile(String username) {
		profileRepository.blockProfile(username);
	}

	@Override
	public void setNotifications(NotificationsSettingsDTO dto) {
		profileRepository.setActivePostNotification(dto.loggedInUsername, dto.followingUsername, dto.activePostNotification);
		profileRepository.setActiveStoryNotification(dto.loggedInUsername, dto.followingUsername, dto.activeStoryNotification);
		profileRepository.setActiveLikesNotification(dto.loggedInUsername, dto.followingUsername, dto.activeLikesNotification);
		profileRepository.setActiveCommentNotification(dto.loggedInUsername, dto.followingUsername, dto.activeCommentNotification);
		profileRepository.setActiveMessageNotification(dto.loggedInUsername, dto.followingUsername, dto.activeMessageNotification);
	}

	@Override
	public boolean getActiveMessageNotification(String startNodeUsername, String endNodeUsername) {
		return profileRepository.getActiveMessageNotification(startNodeUsername, endNodeUsername);
	}

	@Override
	public NotificationsSettingsDTO getNotificationsSettings(String startNodeUsername, String endNodeUsername) {
		NotificationsSettingsDTO dto = new NotificationsSettingsDTO();
		dto.activeLikesNotification = profileRepository.getActiveLikesNotification(startNodeUsername, endNodeUsername);
		dto.activeCommentNotification = profileRepository.getActiveCommentsNotification(startNodeUsername, endNodeUsername);
		dto.activeStoryNotification = profileRepository.getActiveStoryNotification(startNodeUsername, endNodeUsername);
		dto.activePostNotification = profileRepository.getActivePostNotification(startNodeUsername, endNodeUsername);
		dto.activeMessageNotification = profileRepository.getActiveMessageNotification(startNodeUsername, endNodeUsername);		
		return dto;
	}

}

