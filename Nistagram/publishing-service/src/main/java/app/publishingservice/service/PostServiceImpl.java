package app.publishingservice.service;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import app.publishingservice.dto.PostDTO;
import app.publishingservice.event.PostCreatedEvent;
import app.publishingservice.model.Hashtag;
import app.publishingservice.model.Post;
import app.publishingservice.model.Profile;
import app.publishingservice.repository.HashtagRepository;
import app.publishingservice.repository.LocationRepository;
import app.publishingservice.repository.PostRepository;
import app.publishingservice.repository.ProfileRepository;

@Service
public class PostServiceImpl implements PostService {

	private ProfileRepository profileRepository;
	private LocationRepository locationRepository;
	private HashtagRepository hashtagRepository;
	private PostRepository postRepository;
	private final ApplicationEventPublisher publisher;
	
	@Autowired
	public PostServiceImpl(PostRepository postRepository,ProfileRepository profileRepository,
		   LocationRepository locationRepository, HashtagRepository hashtagRepository, ApplicationEventPublisher publisher) {
		this.publisher = publisher;
		this.postRepository = postRepository;
		this.profileRepository = profileRepository;
		this.locationRepository = locationRepository;
		this.hashtagRepository = hashtagRepository;
	}
	
	@Override
	@Transactional
	public void create(PostDTO postDTO) {
		Post post = new Post();
		
		post.setProfile(profileRepository.findByUsername(postDTO.ownerUsername));
		post.setDescription(postDTO.description);
		post.setDeleted(false);
		
		if (postDTO.location != null && !postDTO.location.isEmpty()) {
			post.setLocation(locationRepository.findByName(postDTO.location));
		}	
		
		if (postDTO.hashtags != null && postDTO.hashtags.size() != 0) {
			Set<Hashtag> hashtags = new HashSet<Hashtag>();
			for (String hashtag : postDTO.hashtags) {
				hashtags.add(hashtagRepository.findByName("#" + hashtag));
			}
			post.setHashtags(hashtags);
		}		
		
		if (postDTO.taggedUsernames != null && postDTO.taggedUsernames.size() != 0) {
			Set<Profile> taggedUsernames = new HashSet<Profile>();
			for (String taggedUsername:postDTO.taggedUsernames) {
				taggedUsernames.add(profileRepository.findByUsername(taggedUsername));
			}
			post.setTagged(taggedUsernames);
		}
		
		postRepository.save(post);	
		publish(post, postDTO.files);
	}
	
	private void publish(Post post, List<File> files) {
		PostCreatedEvent event = new PostCreatedEvent(UUID.randomUUID().toString(), post, files);
        publisher.publishEvent(event);
    }

	@Override
	public Collection<Post> getAllByUsername(String username) {
		return postRepository.findAllByUsername(username);
	}

	
	
	
}
