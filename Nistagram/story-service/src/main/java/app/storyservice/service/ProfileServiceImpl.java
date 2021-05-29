package app.storyservice.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.storyservice.dto.ProfileDTO;
import app.storyservice.model.Profile;
import app.storyservice.model.Story;
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
		// izvuci username trenutnog korisnika iz tokena
		String username = "trenutni";
		// pozvati follower servis da dobijem sve koje trenutni prati
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

}
