package app.publishingservice.service;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import app.publishingservice.event.ProfileCanceledEvent;
import app.publishingservice.dto.ProfileDTO;
import app.publishingservice.model.Post;
import app.publishingservice.model.Profile;
import app.publishingservice.model.Story;
import app.publishingservice.repository.PostRepository;
import app.publishingservice.repository.ProfileRepository;
import app.publishingservice.repository.StoryRepository;

@Service
public class ProfileServiceImpl implements ProfileService {
	
	private ProfileRepository profileRepository;
	private final ApplicationEventPublisher publisher;
	private PostRepository postRepository;
	private StoryRepository storyRepository;
	
	@Autowired
	public ProfileServiceImpl(ProfileRepository profileRepository, ApplicationEventPublisher publisher,
			PostRepository postRepository, StoryRepository storyRepository) {
		this.profileRepository = profileRepository;
		this.publisher = publisher;
		this.postRepository = postRepository;
		this.storyRepository = storyRepository;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void create(ProfileDTO profileDTO) throws Exception {
		Profile profile = new Profile();
		
		profile.setUsername(profileDTO.username);
		profile.setPublic(profileDTO.isPublic);
		profile.setAllowedTagging(profileDTO.allowedTagging);
		profile.setBlocked(false);
		
		profileRepository.save(profile);
				
		publishProfileCanceled(profileDTO.username, "An error occurred while creating profile in publishing service.");
	}
	
	private void publishProfileCanceled(String username, String reason) {
		ProfileCanceledEvent event = new ProfileCanceledEvent(UUID.randomUUID().toString(), username,reason);     
        publisher.publishEvent(event);
    }
	
	@Override
	@Transactional
	public void updatePersonalData(String oldUsername, ProfileDTO profileDTO) {
		Profile profile = profileRepository.findByUsername(oldUsername);
				
		profile.setUsername(profileDTO.username);
		
		profileRepository.save(profile);
	}
	
	@Override
	@Transactional
	public void updateProfilePrivacy(ProfileDTO profileDTO) {
		Profile profile = profileRepository.findByUsername(profileDTO.username);
		
		profile.setPublic(profileDTO.isPublic);
		profile.setAllowedTagging(profileDTO.allowedTagging);
		
		profileRepository.save(profile);
	}
	
	@Override
	@Transactional
	public void blockProfile(String username) {
		Profile profile = profileRepository.findByUsername(username);
		profile.setBlocked(true);
		profileRepository.save(profile);
	}
	
	@Override
	public boolean existsByUsername(String username) {
		return profileRepository.existsByUsername(username);
	}

	@Override
	public ProfileDTO findByUsername(String username) {
		Profile profile = profileRepository.findByUsername(username);
		return new ProfileDTO(profile.getUsername(), profile.isPublic(), profile.isAllowedTagging(), profile.isBlocked());
	}

	@Override
	public Long getIdByUsername(String ownerUsername) {
		return profileRepository.findByUsername(ownerUsername).getId();
	}

	@Override
	@Transactional
	public void deleteByUsername(String username) {
		Profile profile = profileRepository.findByUsername(username);
		if(profile != null)
			profileRepository.delete(profile);
		
	}

	@Override
	public ProfileDTO getOwnerOfPost(Long postId) {
		Post post = postRepository.findById(postId).get();
		return new ProfileDTO(post.getProfile().getUsername(), post.getProfile().isPublic(), post.getProfile().isAllowedTagging(), post.getProfile().isBlocked());
	}

	@Override
	public ProfileDTO getOwnerOfStory(Long storyId) {
		Story story = storyRepository.findById(storyId).get();
		return new ProfileDTO(story.getOwner().getUsername(), story.getOwner().isPublic(), story.getOwner().isAllowedTagging(), story.getOwner().isBlocked());
	}

}
