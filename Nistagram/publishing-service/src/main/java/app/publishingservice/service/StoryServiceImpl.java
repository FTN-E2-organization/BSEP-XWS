package app.publishingservice.service;


import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.publishingservice.dto.AddStoryDTO;
import app.publishingservice.model.*;
import app.publishingservice.repository.*;

@Service
public class StoryServiceImpl implements StoryService {

	private StoryRepository storyRepository;
	private ProfileRepository profileRepository;
	private LocationRepository locationRepository;
	private HashtagRepository hashtagRepository;
	
	@Autowired
	public StoryServiceImpl(StoryRepository storyRepository,ProfileRepository profileRepository,
		   LocationRepository locationRepository, HashtagRepository hashtagRepository) {
		this.storyRepository = storyRepository;
		this.profileRepository = profileRepository;
		this.locationRepository = locationRepository;
		this.hashtagRepository = hashtagRepository;
	}

	@Override
	public void create(AddStoryDTO storyDTO) {
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
				hashtags.add(hashtagRepository.findByName(hashtag));
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
		
		storyRepository.save(story);
	}
}
