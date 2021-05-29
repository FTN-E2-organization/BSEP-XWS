package app.storyservice.service;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.storyservice.dto.AddStoryDTO;
import app.storyservice.dto.ProfileDTO;
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
		// proveri da li ulogovani prati ovog sa username
		Profile profile = profileRepository.getProfileByUsername(username);
		if (!profile.isPublic()) {
			// proveri da li se prate preko follow service
		}
		return storyRepository.getStoryByProfile(profile);

	}

	@Override
	public void create(AddStoryDTO storyDTO) {
		ProfileDTO profileDto = storyDTO.getProfileDto();
		Profile p = profileRepository.getProfileByUsername(profileDto.getUsername());
		// dodati sagu da u bazi imam sve profile(zbog problema ako promeni da li je
		// javan profil) pa obrisati if
		if (p == null) {
			p = new Profile();
			p.setUsername(profileDto.getUsername());
			p.setPublic(profileDto.isPublic());
			profileRepository.save(p);
		}
		Story s = new Story();
		if (storyDTO.getTimestamp() != null) {
			s.setTimestamp(storyDTO.getTimestamp());
		} else {
			s.setTimestamp(LocalDateTime.now());
		}
		s.setDeleted(false);
		s.setDescription(storyDTO.getDescription());
		s.setForCloseFriends(storyDTO.isForCloseFriends());
		s.setId(storyDTO.getId());
		s.setLocation(storyDTO.getLocation());

		s.setTags(storyDTO.getTags());
		storyRepository.save(s);
	}

}
