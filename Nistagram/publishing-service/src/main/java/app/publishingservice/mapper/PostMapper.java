package app.publishingservice.mapper;

import java.util.ArrayList;
import java.util.Collection;

import app.publishingservice.dto.PostDTO;
import app.publishingservice.model.Hashtag;
import app.publishingservice.model.Post;
import app.publishingservice.model.Profile;

public class PostMapper {

	public static Collection<PostDTO> toPostDTOs(Collection<Post> posts) {
		
		Collection<PostDTO> postDTOs = new ArrayList<>();
		for (Post p : posts) {			
			postDTOs.add(toPostDTO(p));
		}		
		return postDTOs;
	}
	
	public static PostDTO toPostDTO(Post p) {
		
		PostDTO dto = new PostDTO();
		dto.id = p.getId();
		dto.ownerUsername = p.getProfile().getUsername();	
		dto.description = p.getDescription();			
		if (p.getLocation() != null) {
			dto.location = p.getLocation().getName();
		}						
		dto.timestamp = p.getTimestamp();
		if (p.getHashtags() != null) {
			for (Hashtag hashtag : p.getHashtags()) {
				dto.hashtags.add(hashtag.getName());
			}
		}	
		if (p.getTagged() != null) {
			for (Profile tagged : p.getTagged()) {
				dto.taggedUsernames.add(tagged.getUsername());
			}
		}														
		return dto;
	}
	
}
