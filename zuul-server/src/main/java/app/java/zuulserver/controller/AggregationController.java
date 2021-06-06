package app.java.zuulserver.controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.java.zuulserver.client.AuthClient;
import app.java.zuulserver.client.FollowingClient;
import app.java.zuulserver.client.MediaClient;
import app.java.zuulserver.client.PublishingClient;
import app.java.zuulserver.dto.ContentDTO;
import app.java.zuulserver.dto.MediaDTO;
import app.java.zuulserver.dto.PostDTO;
import app.java.zuulserver.dto.ProfileDTO;
import app.java.zuulserver.dto.ProfileOverviewDTO;
import app.java.zuulserver.dto.StoryDTO;
import app.java.zuulserver.dto.UploadInfoDTO;
import app.java.zuulserver.enums.ContentType;

@RestController
@RequestMapping(value = "api/aggregation")
public class AggregationController {
	
	private FollowingClient followingClient;
	private AuthClient authClient;
	private PublishingClient publishingClient;
	private MediaClient mediaClient;
	
	@Autowired
	public AggregationController(FollowingClient followingClient, AuthClient authClient, PublishingClient publishingClient, 
			MediaClient mediaClient ) {
		this.followingClient = followingClient;
		this.authClient = authClient;
		this.publishingClient = publishingClient;
		this.mediaClient = mediaClient;
	}
	
	@GetMapping("/profile-overview/{username}")
	public ResponseEntity<?> getProfileInfo(@PathVariable String username){
		
		try {
			ProfileOverviewDTO profileOverviewDTO = this.authClient.getProfile(username);
			Collection<String> followers = new ArrayList<>();
			Collection<String> following = new ArrayList<>();
			Collection<ProfileDTO> profileFollowingDTOs = this.followingClient.getFollowing(username);
			for(ProfileDTO f: profileFollowingDTOs) {
				following.add(f.username);
			}
			Collection<ProfileDTO> profileFollowersDTOs = this.followingClient.getFollowers(username);
			for(ProfileDTO f: profileFollowersDTOs) {
				followers.add(f.username);
			}
			profileOverviewDTO.followers = followers;
			profileOverviewDTO.following = following;
		
			
			return new ResponseEntity<ProfileOverviewDTO>(profileOverviewDTO, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/posts/{username}")
	public ResponseEntity<?> getPostsByUsername(@PathVariable String username){
		try {
			Collection<MediaDTO> mediaDTOs= new ArrayList<>();
			Collection<PostDTO> postDTOs = this.publishingClient.getPostsByUsername(username);
			for(PostDTO p: postDTOs) {
				Collection<MediaDTO> media = this.mediaClient.getMediaById(p.id, ContentType.post);
				for(MediaDTO m: media) {
					mediaDTOs.add(m);
				}	
			}
			
			return new ResponseEntity<Collection<MediaDTO>>(mediaDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/highlight/{username}")
	public ResponseEntity<?> getHighlightStoriesByUsername(@PathVariable String username){
		
		try {
			Collection<MediaDTO> mediaDTOs= new ArrayList<>();
			Collection<StoryDTO> storyDTOs = this.publishingClient.getHighlightStoriesByUsername(username);
			for(StoryDTO s: storyDTOs) {
				Collection<MediaDTO> media = this.mediaClient.getMediaById(s.id, ContentType.story);
				for(MediaDTO m: media) {
					mediaDTOs.add(m);
				}
			}
			return new ResponseEntity<Collection<MediaDTO>>(mediaDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	
	@GetMapping("/search")
	public ResponseEntity<?> getProfilesAndLocationsAndHastags(){
		
		try {
			Collection<ContentDTO> contentDTOs= new ArrayList<>();
						
			Collection<String> locations = this.publishingClient.getLocations(); //dobavlja sve lokacije		
			for(String location : locations) {
				ContentDTO dto = new ContentDTO();
				dto.contentName = location;
				dto.section = "location";
				contentDTOs.add(dto);
			}					
			Collection<String> hashtags = this.publishingClient.getHashtags(); //dobavlja sve hastagove		
			for(String hashtag : hashtags) {
				ContentDTO dto = new ContentDTO();
				dto.contentName = hashtag;
				dto.section = "hashtag";
				contentDTOs.add(dto);
			}				
			Collection<ProfileDTO> profileDTOs = this.authClient.getProfiles(); //dobavlja sve profile		
			for(ProfileDTO p : profileDTOs) {
				ContentDTO dto = new ContentDTO();
				dto.contentName = p.username;
				dto.section = "profile";
				contentDTOs.add(dto);
			}				
			return new ResponseEntity<Collection<ContentDTO>>(contentDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/location-overview/{locationName}")
	public ResponseEntity<?> getPostByLocationName(@PathVariable String locationName){		
		try {
			Collection<MediaDTO> mediaDTOs= new ArrayList<>();
			Collection<PostDTO> postDTOs = this.publishingClient.getPostsByLocationName(locationName);
			for(PostDTO s: postDTOs) {
				Collection<MediaDTO> media = this.mediaClient.getMediaById(s.id, ContentType.post);
				for(MediaDTO m: media) {
					mediaDTOs.add(m);
				}				
			}										
			return new ResponseEntity<Collection<MediaDTO>>(mediaDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/hashtag/{hashtag}")
	public ResponseEntity<?> getPostByHashtag(@PathVariable String hashtag){		
		try {
			Collection<MediaDTO> mediaDTOs= new ArrayList<>();
			Collection<PostDTO> postDTOs = this.publishingClient.getPostsByHashtag(hashtag);
			for(PostDTO s: postDTOs) {
				Collection<MediaDTO> media = this.mediaClient.getMediaById(s.id, ContentType.post);
				for(MediaDTO m: media) {
					mediaDTOs.add(m);
				}				
			}										
			return new ResponseEntity<Collection<MediaDTO>>(mediaDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/files-upload")
	public ModelAndView uploadFile(@FormParam("file") MultipartFile[] file, @QueryParam(value = "idContent") Long idContent, @QueryParam(value = "type") ContentType type) {
		try {
			String uploadInfoJson = new ObjectMapper().writeValueAsString(new UploadInfoDTO(idContent, type));
			
			for(MultipartFile f:file) 
				mediaClient.fileUpload(f, uploadInfoJson);
				
			return new ModelAndView("redirect:" + "http://localhost:8111/html/profile.html");
		}catch (Exception e) {
			return new ModelAndView("redirect:" + "http://localhost:8111/html/publishPost.html");
		}
	}
		
	
	@GetMapping("/post/{postId}")
	public ResponseEntity<?> getPostById(@PathVariable long postId){		
		try {			
			PostDTO postDTO = this.publishingClient.getPostById(postId);
			
//			Collection<MediaDTO> mediaDTOs= new ArrayList<>();
//			Collection<MediaDTO> media = this.mediaClient.getMediaById(postDTO.id, ContentType.post);
//			for(MediaDTO m: media) {
//				mediaDTOs.add(m);
//			}		
			
			
			
										
			return new ResponseEntity<PostDTO>(postDTO,  HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
