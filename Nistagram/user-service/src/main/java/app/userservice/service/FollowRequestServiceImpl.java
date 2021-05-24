package app.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.userservice.dto.*;
import app.userservice.model.*;
import app.userservice.repository.*;

@Service
public class FollowRequestServiceImpl implements FollowRequestService {

	private FollowRequestRepository followRequestRepository;
	private ProfileRepository profileRepository;
	
	@Autowired
	public FollowRequestServiceImpl(FollowRequestRepository followRequestRepository, ProfileRepository profileRepository) {
		this.followRequestRepository = followRequestRepository;
		this.profileRepository = profileRepository;
	}

	@Override
	public void create(FollowRequestDTO followRequestDTO) {
		FollowRequest followRequest = new FollowRequest();
		
		followRequest.setBase(profileRepository.findById(followRequestDTO.baseProfileId).get());
		followRequest.setFollower(profileRepository.findById(followRequestDTO.followerProfileId).get());
		followRequest.setApproved(false);
		
		followRequestRepository.save(followRequest);
	}
}
