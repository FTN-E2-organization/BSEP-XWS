package app.storyservice.service;

import java.time.LocalDateTime;
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
	public Collection<Story> getStoriesByProfileUsername(String username) {
		// proveri da li ulogovani prati ovog sa username -> gateway
		Profile profile = profileRepository.getProfileByUsername(username);
		if (!profile.isPublic()) {
			// proveri da li se prate preko follow service -> gateway
		}
		return storyRepository.getStoryByProfile(profile);

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
		s.setTagged(storyDTO.getTagged());
		
		storyRepository.save(s);
	}

}
