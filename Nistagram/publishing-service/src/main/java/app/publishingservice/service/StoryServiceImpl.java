package app.publishingservice.service;

import java.util.*;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import app.publishingservice.dto.StoryDTO;
import app.publishingservice.event.StoryEvent;
import app.publishingservice.event.StoryEventType;
import app.publishingservice.exception.BadRequest;
import app.publishingservice.model.*;
import app.publishingservice.repository.*;

@Service
public class StoryServiceImpl implements StoryService {

	private StoryRepository storyRepository;
	private ProfileRepository profileRepository;
	private LocationRepository locationRepository;
	private HashtagRepository hashtagRepository;
	private final ApplicationEventPublisher publisher;
	
	@Autowired
	public StoryServiceImpl(StoryRepository storyRepository,ProfileRepository profileRepository,
		   LocationRepository locationRepository, HashtagRepository hashtagRepository, ApplicationEventPublisher publisher) {
		this.publisher = publisher;
		this.storyRepository = storyRepository;
		this.profileRepository = profileRepository;
		this.locationRepository = locationRepository;
		this.hashtagRepository = hashtagRepository;
	}

	@Override
	@Transactional
	public Long create(StoryDTO storyDTO) {
		Story story = new Story();
	
		story.setOwner(profileRepository.findByUsername(storyDTO.ownerUsername));
		story.setDescription(storyDTO.description);
		story.setHighlight(storyDTO.isHighlight);
		story.setForCloseFriends(storyDTO.forCloseFriends);
		story.setDeleted(false);
		
		if(storyDTO.location != null && !storyDTO.location.isEmpty()) {
			story.setLocation(locationRepository.findByName(storyDTO.location));
		}
		if(storyDTO.hashtags != null && storyDTO.hashtags.size() != 0) {
			Set<Hashtag> hashtags = new HashSet<Hashtag>();
			for(String hashtag:storyDTO.hashtags) {
				hashtags.add(hashtagRepository.findByName("#" + hashtag));
			}
			story.setHashtags(hashtags);
		}
		
		if(storyDTO.taggedUsernames != null && storyDTO.taggedUsernames.size() != 0) {
			Set<Profile> taggedUsernames = new HashSet<Profile>();
			for(String taggedUsername:storyDTO.taggedUsernames) {
				taggedUsernames.add(profileRepository.findByUsername(taggedUsername));
			}
			story.setTagged(taggedUsernames);
		}
		
		Story savedStory =  storyRepository.save(story);
		publishStoryCreatedEvent(savedStory, storyDTO);
		
		return savedStory.getId();
	}
	
	private void publishStoryCreatedEvent(Story savedStory, StoryDTO storyDTO) {
		storyDTO.id = savedStory.getId();
		storyDTO.timestamp = savedStory.getTimestamp();
		storyDTO.isDeleted = false;
		StoryEvent event = new StoryEvent(UUID.randomUUID().toString(), storyDTO, StoryEventType.created);
        publisher.publishEvent(event);
    }
	

	@Override
	public Collection<Story> getHighlightStoriesByUsername(String username) {
		return storyRepository.findHighlightStoriesByUsername(username);
	}

	@Override
	public Story getById(long storyId) throws Exception{
		Story story = storyRepository.getOne(storyId);
		
		if(story.isDeleted())
			throw new BadRequest("The story is blocked");
		else
			return story;
	}

	@Override
	@Transactional
	public void delete(Long storyId) {
		Story story = storyRepository.findById(storyId).get();
		story.setDeleted(true);
		storyRepository.save(story);
		
		publishStoryDeletedEvent(storyId);
	}
	
	private void publishStoryDeletedEvent(Long storyId) {
		StoryEvent event = new StoryEvent(UUID.randomUUID().toString(), storyId, StoryEventType.deleted);
        publisher.publishEvent(event);
    }
}
