package app.storyservice.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.storyservice.dto.ProfileDTO;
import app.storyservice.model.Profile;
import app.storyservice.repository.ProfileRepository;
import app.storyservice.repository.StoryRepository;

@Service
public class ProfileServiceImpl implements ProfileService {

	private StoryRepository storyRepository;
	private ProfileRepository profileRepository;

	@Autowired
	public ProfileServiceImpl(ProfileRepository profileRepository, StoryRepository storyRepository) {
		this.profileRepository = profileRepository;
		this.storyRepository = storyRepository;
	}

	@Override
	public Collection<Profile> getAllProfiles() {
		// izvuci username trenutnog korisnika iz tokena - ovo ce se raditi u kontroleru
		String username = "trenutni";
		// pozvati follower servis da dobijem sve koje trenutni prati - ovo ide u gateway
		List<ProfileDTO> profiles = new LinkedList<>();
		ProfileDTO d = new ProfileDTO();
		d.setUsername("prvi");
		ProfileDTO da = new ProfileDTO();
		da.setUsername("drugi");
		profiles.add(da);
		profiles.add(d);
		List<String> following = new LinkedList<>();
		for (int i = 0; i < profiles.size(); i++) {
			following.add(profiles.get(i).getUsername());
		}
		Set<Profile> storyProfiles = profileRepository.getProfileByUsernameIn(following);
		return storyRepository.getStoryByProfileIn(storyProfiles).stream().map(story -> story.getProfile()).distinct()
				.collect(Collectors.toSet());

	}

	@Override
	@Transactional
	public void create(ProfileDTO profileDTO) {
		Profile profile = new Profile();
		
		profile.setUsername(profileDTO.getUsername());
		profile.setPublic(profileDTO.isPublic());
		profile.setDeleted(profileDTO.isDeleted());
		
		profileRepository.save(profile);
	}
}
