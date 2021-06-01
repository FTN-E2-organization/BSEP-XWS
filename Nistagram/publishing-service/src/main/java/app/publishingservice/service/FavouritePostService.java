package app.publishingservice.service;

import java.util.Collection;

import app.publishingservice.dto.AddFavouritePostDTO;
import app.publishingservice.model.FavouritePost;

public interface FavouritePostService {

	void create(AddFavouritePostDTO favouritePostDTO);

	Collection<FavouritePost> getAllByUsername(String username);

	Collection<FavouritePost> getAllByUsernameAndCollection(String username, String collectionName);
	
}
