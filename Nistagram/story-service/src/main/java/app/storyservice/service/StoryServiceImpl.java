package app.storyservice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.storyservice.dto.StoryDTO;
import app.storyservice.model.Profile;
import app.storyservice.model.Story;
import app.storyservice.repository.ProfileRepository;
import app.storyservice.repository.StoryRepository;

@Service
public class StoryServiceImpl implements StoryService {

	private StoryRepository storyRepository;
	private ProfileRepository profileRepository;

	@Autowired
	public StoryServiceImpl(ProfileRepository profileRepository, StoryRepository storyRepository) {
		this.profileRepository = profileRepository;
		this.storyRepository = storyRepository;
	}

	@Override
	public Story getStoryById(Long id) {
		return storyRepository.getStoryById(id);
	}

	@Override
	public Collection<StoryDTO> getStoriesByProfileUsername(String username) {
		// proveri da li ulogovani 
		Profile profile = profileRepository.getProfileByUsername(username);
		Collection<Story> stories = storyRepository.getStoryByProfile(profile);
		Collection<StoryDTO> storydtos = new ArrayList<>();
		for(Story s: stories) {
			StoryDTO dto = new StoryDTO();
			dto.setDeleted(s.isDeleted());
			dto.setDescription(s.getDescription());
			dto.setForCloseFriends(s.isForCloseFriends());
			dto.setHashtags(s.getHashtags());
			dto.setId(s.getId());
			dto.setLocation(s.getLocation());
			dto.setOwnerUsername(s.getProfile().getUsername());
			dto.setTaggedUsernames(s.getTagged());
			dto.setTimestamp(s.getTimestamp());
			storydtos.add(dto);
		}
		return storydtos;

	}

	@Override
	@Transactional
	public void create(StoryDTO storyDTO) {
		Profile p = profileRepository.getProfileByUsername(storyDTO.getOwnerUsername());
		Story s = new Story();
	
		s.setId(storyDTO.getId());
		s.setTimestamp(LocalDateTime.now());
		s.setDeleted(false);
		s.setDescription(storyDTO.getDescription());
		s.setForCloseFriends(storyDTO.isForCloseFriends());
		s.setId(storyDTO.getId());
		s.setLocation(storyDTO.getLocation());
		s.setProfile(p);
		s.setHashtags(storyDTO.getHashtags());
		s.setTagged(storyDTO.getTaggedUsernames());
		
		storyRepository.save(s);
	}

}
