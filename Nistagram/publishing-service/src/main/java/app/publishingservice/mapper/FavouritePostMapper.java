package app.publishingservice.mapper;

import java.util.ArrayList;
import java.util.Collection;

import app.publishingservice.dto.FavouritePostDTO;
import app.publishingservice.model.FavouritePost;
import app.publishingservice.model.Profile;

public class FavouritePostMapper {

	public static Collection<FavouritePostDTO> toFavouritePostDTOs(Collection<FavouritePost> favouritePosts) {
		Collection<FavouritePostDTO> favouritePostDTOs = new ArrayList<>();
		for (FavouritePost fp : favouritePosts) {
			
			FavouritePostDTO dto = new FavouritePostDTO();
			dto.id = fp.getId();
			dto.postId = fp.getPost().getId();		    
			dto.postTimeStamp = fp.getPost().getTimestamp();
			dto.description = fp.getPost().getDescription();
			dto.collectionName = fp.getCollection().getName();
			dto.postOwnerUsername = fp.getPost().getProfile().getUsername();			
			for (Profile tagged : fp.getPost().getTagged()) {
				dto.postTaggedUsernames.add(tagged.getUsername());
			}			
			
			favouritePostDTOs.add(dto);
		}		
		return favouritePostDTOs;
	}
	
}
