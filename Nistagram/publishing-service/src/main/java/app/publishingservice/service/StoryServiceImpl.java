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
	
	@Autowired
	public StoryServiceImpl(StoryRepository storyRepository,ProfileRepository profileRepository) {
		this.storyRepository = storyRepository;
		this.profileRepository = profileRepository;
	}

	@Override
	public void create(AddStoryDTO storyDTO) {
		/*U kontroleru ce se pokupiti id trenutno ulogovanog korisnika i poslati*/
		Story story = new Story();
		
		story.setOwner(profileRepository.findById(storyDTO.ownerId).get());
		story.setDescription(storyDTO.description);
		story.setHighlight(storyDTO.isHighlight);
		story.setForCloseFriends(storyDTO.forCloseFriends);
		story.setDeleted(false);
		
		if(!storyDTO.location.isEmpty() && storyDTO.location != null) {
			Location location = new Location();
			location.setName(storyDTO.location);
			story.setLocation(location);
		}
		
		if(storyDTO.hashtags.size() != 0) {
			Set<Hashtag> hashtags = new HashSet<Hashtag>();
			for(String h:storyDTO.hashtags) {
				Hashtag hashtag = new Hashtag();
				hashtag.setName(h);
				hashtags.add(hashtag);
			}
			story.setHashtags(hashtags);
		}
		
		if(storyDTO.tagged.size() != 0) {
			Set<Profile> tagged = new HashSet<Profile>();
			for(Long t:storyDTO.tagged) {
				Profile profile = new Profile();
				
			}
		}
		
		storyRepository.save(story);
	}
}
