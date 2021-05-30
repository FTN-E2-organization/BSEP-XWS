package app.publishingservice.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.publishingservice.dto.AddFavouritePostDTO;
import app.publishingservice.model.FavouritePost;
import app.publishingservice.repository.CollectionRepository;
import app.publishingservice.repository.FavouritePostRepository;
import app.publishingservice.repository.PostRepository;
import app.publishingservice.repository.ProfileRepository;
import app.publishingservice.model.Collection;

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
	public void create(AddFavouritePostDTO favouritePostDTO) {			//******************* dodati proveru da li favourite post postoji vec
		FavouritePost favouritePost = new FavouritePost();
		favouritePost.setOwner(profileRepository.findByUsername(favouritePostDTO.ownerUsername));		
		favouritePost.setPost(postRepository.getOne(favouritePostDTO.postId));
				
		if(favouritePostDTO.collectionName != null && !favouritePostDTO.collectionName.isEmpty()) {
			Set<Collection> collections = new HashSet<Collection>();
			collections.add(collectionRepository.findByName(favouritePostDTO.collectionName));
		}
		
		favouritePostRepository.save(favouritePost);	
	}		
	
	
}
