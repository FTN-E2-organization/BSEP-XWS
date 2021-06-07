package app.publishingservice.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.publishingservice.dto.AddFavouritePostDTO;
import app.publishingservice.model.FavouritePost;
import app.publishingservice.repository.CollectionRepository;
import app.publishingservice.repository.FavouritePostRepository;
import app.publishingservice.repository.PostRepository;
import app.publishingservice.repository.ProfileRepository;

@Service
public class FavouritePostServiceImpl implements FavouritePostService {

	private FavouritePostRepository favouritePostRepository;
	private ProfileRepository profileRepository;
	private PostRepository postRepository;
	private CollectionRepository collectionRepository;
	
	@Autowired
	public FavouritePostServiceImpl(FavouritePostRepository favouritePostRepository, ProfileRepository profileRepository, 
									PostRepository postRepository, CollectionRepository collectionRepository) {
		this.favouritePostRepository = favouritePostRepository;
		this.profileRepository = profileRepository;
		this.postRepository = postRepository;
		this.collectionRepository = collectionRepository;
	}

	@Override
	public void create(AddFavouritePostDTO favouritePostDTO) {
		
		FavouritePost favouritePost = favouritePostRepository.getByCollectionNameAndPostId(favouritePostDTO.collectionName, favouritePostDTO.postId, favouritePostDTO.ownerUsername);
		
		if (favouritePost != null) {
			return;
		}
		
		favouritePost = new FavouritePost();
		favouritePost.setOwner(profileRepository.findByUsername(favouritePostDTO.ownerUsername));		
		favouritePost.setPost(postRepository.getOne(favouritePostDTO.postId));
				
		if(favouritePostDTO.collectionName != null && !favouritePostDTO.collectionName.isEmpty()) {
			favouritePost.setCollection(collectionRepository.findByName(favouritePostDTO.collectionName));
		}
		
		favouritePostRepository.save(favouritePost);	
	}

	@Override
	public Collection<FavouritePost> getAllByUsername(String username) {
		return favouritePostRepository.getAllByUsername(username);
	}

	@Override
	public Collection<FavouritePost> getAllByUsernameAndCollection(String username, String collectionName) {
		return favouritePostRepository.getAllByUsernameAndCollection(username, collectionName);
	}		
	
	
	
	
}
