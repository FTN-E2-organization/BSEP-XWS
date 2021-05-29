package app.publishingservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.publishingservice.dto.AddFavouritePostDTO;
import app.publishingservice.model.FavouritePost;
import app.publishingservice.repository.FavouritePostRepository;
import app.publishingservice.repository.PostRepository;
import app.publishingservice.repository.ProfileRepository;

@Service
public class FavouritePostServiceImpl implements FavouritePostService {

	private FavouritePostRepository favouritePostRepository;
	private ProfileRepository profileRepository;
	private PostRepository postRepository;
	
	@Autowired
	public FavouritePostServiceImpl(FavouritePostRepository favouritePostRepository, ProfileRepository profileRepository, PostRepository postRepository) {
		this.favouritePostRepository = favouritePostRepository;
		this.profileRepository = profileRepository;
		this.postRepository = postRepository;
	}

	@Override
	public void create(AddFavouritePostDTO favouritePostDTO) {
		FavouritePost favouritePost = new FavouritePost();
		
		favouritePost.setOwner(profileRepository.findByUsername(favouritePostDTO.ownerUsername));
//		favouritePost.setPost(postRepository.findById(favouritePostDTO.postId));
		favouritePostRepository.save(favouritePost);
		
	}	
	
	
	
	
}
