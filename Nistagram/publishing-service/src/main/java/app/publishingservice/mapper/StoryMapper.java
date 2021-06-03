package app.publishingservice.mapper;

import java.util.ArrayList;
import java.util.Collection;

import app.publishingservice.dto.StoryDTO;
import app.publishingservice.model.Hashtag;
import app.publishingservice.model.Profile;
import app.publishingservice.model.Story;

public class StoryMapper {
	
	public static Collection<StoryDTO> toStoryDTOs(Collection<Story> stories) {
		
		Collection<StoryDTO> storyDTOs = new ArrayList<>();
		for (Story s : stories) {			
			StoryDTO dto = new StoryDTO();
			dto.id = s.getId();
			dto.ownerUsername = s.getOwner().getUsername();	
			dto.description = s.getDescription();	
			dto.isHighlight = s.isHighlight();
			dto.forCloseFriends = s.isForCloseFriends();
			if (s.getLocation() != null) {
				dto.location = s.getLocation().getName();
			}						
			dto.timestamp = s.getTimestamp();
			if (s.getHashtags() != null) {
				for (Hashtag hashtag : s.getHashtags()) {
					dto.hashtags.add(hashtag.getName());
				}
			}	
			if (s.getTagged() != null) {
				for (Profile tagged : s.getTagged()) {
					dto.taggedUsernames.add(tagged.getUsername());
				}
			}										
			storyDTOs.add(dto);
		}		
		return storyDTOs;
	}	

}
