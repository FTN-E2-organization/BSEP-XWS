package app.java.zuulserver.client;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import app.java.zuulserver.dto.FavouritePostDTO;
import app.java.zuulserver.dto.PostDTO;
import app.java.zuulserver.dto.ProfileDTO;
import app.java.zuulserver.dto.StoryDTO;

@FeignClient(name = "publishing-service")
public interface PublishingClient {
	
	@GetMapping("api/publishing/post/{postId}")
	PostDTO getPostById(@PathVariable("postId") long postId);
	
	@GetMapping("api/publishing/post/username/{username}")
	Collection<PostDTO> getPostsByUsername(@PathVariable("username") String username);
	
	@GetMapping("api/publishing/story/highlight/{username}")
	Collection<StoryDTO> getHighlightStoriesByUsername(@PathVariable("username") String username);
	
	@PutMapping("api/publishing/post/delete/{postId}")
	public void deletePost(@PathVariable long postId);

	@GetMapping("api/publishing/location")
	Collection<String> getLocations();
	
	@GetMapping("api/publishing/hashtag")
	Collection<String> getHashtags();
	
	@GetMapping("api/publishing/post/location/{locationName}")
	Collection<PostDTO> getPostsByLocationName(@PathVariable("locationName") String locationName);

	@GetMapping("api/publishing/post/hashtag/{hashtagName}")
	Collection<PostDTO> getPostsByHashtag(@PathVariable("hashtagName") String hashtagName);

	@GetMapping("api/publishing/favourite-post/{username}/by-collection/{collectionName}")
	Collection<FavouritePostDTO> getPostsByCollectionName(@PathVariable("collectionName") String collectionName, @PathVariable("username") String username);

	@GetMapping("api/publishing/favourite-post/{username}")
	Collection<FavouritePostDTO> getAllFavouritePosts(@PathVariable("username") String username);
	
	@GetMapping("api/publishing/profile/post-owner/{id}")
	ProfileDTO getOwnerOfPost(@PathVariable("id") Long id);
	
	@GetMapping("api/publishing/profile/story-owner/{id}")
	ProfileDTO getOwnerOfStory(@PathVariable("id") Long id);
	
}
