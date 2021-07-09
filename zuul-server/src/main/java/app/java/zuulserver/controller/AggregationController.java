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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.java.zuulserver.client.ActivityClient;
import app.java.zuulserver.client.AuthClient;
import app.java.zuulserver.client.CampaignClient;
import app.java.zuulserver.client.FollowingClient;
import app.java.zuulserver.client.MediaClient;
import app.java.zuulserver.client.NotificationClient;
import app.java.zuulserver.client.PublishingClient;
import app.java.zuulserver.client.StoryClient;
import app.java.zuulserver.dto.AdDTO;
import app.java.zuulserver.dto.CampaignDTO;
import app.java.zuulserver.dto.ContentDTO;
import app.java.zuulserver.dto.FavouritePostDTO;
import app.java.zuulserver.dto.MediaContentDTO;
import app.java.zuulserver.dto.MediaDTO;
import app.java.zuulserver.dto.MessageDTO;
import app.java.zuulserver.dto.MonitoringDTO;
import app.java.zuulserver.dto.NotificationDTO;
import app.java.zuulserver.dto.NumberOfClicksDTO;
import app.java.zuulserver.dto.NumberOfReactionsDTO;
import app.java.zuulserver.dto.PostDTO;
import app.java.zuulserver.dto.ProfileCategoryDTO;
import app.java.zuulserver.dto.ProfileDTO;
import app.java.zuulserver.dto.ProfileOverviewDTO;
import app.java.zuulserver.dto.ReactionDTO;
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
	private StoryClient storyClient;
	private ActivityClient activityClient;
	private NotificationClient notificationClient;
	private CampaignClient campaignClient;
	
	@Autowired
	public AggregationController(FollowingClient followingClient, AuthClient authClient, PublishingClient publishingClient, 
			MediaClient mediaClient, ActivityClient activityClient, StoryClient storyClient, NotificationClient notificationClient, 
			CampaignClient campaignClient) {
		this.followingClient = followingClient;
		this.authClient = authClient;
		this.publishingClient = publishingClient;
		this.mediaClient = mediaClient;
		this.storyClient = storyClient;
		this.activityClient = activityClient;
		this.notificationClient = notificationClient;
		this.campaignClient = campaignClient;
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
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
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

	
	@GetMapping("/search/{typeOfSearch}")
	public ResponseEntity<?> getProfilesAndLocationsAndHastags(@PathVariable String typeOfSearch){
		
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
			Collection<ProfileDTO> profileDTOs = this.authClient.getProfiles(typeOfSearch); //dobavlja sve profile		
			for(ProfileDTO p : profileDTOs) {
				ContentDTO dto = new ContentDTO();
				dto.contentName = p.username;
				dto.section = "profile";
				contentDTOs.add(dto);
			}				
			return new ResponseEntity<Collection<ContentDTO>>(contentDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
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
			
			return new ModelAndView("redirect:" + "https://localhost:8111/html/index.html");
		}catch (Exception e) {				
			return new ModelAndView("redirect:" + "https://localhost:8111/html/publishPost.html");
		}
	}
	
	
	@PostMapping("/files-upload/ad")
	public ModelAndView uploadFileAd(@FormParam("file") MultipartFile[] file, @QueryParam(value = "idContent") Long idContent, @QueryParam(value = "type") ContentType type) {
		try {
			String uploadInfoJson = new ObjectMapper().writeValueAsString(new UploadInfoDTO(idContent, type));
			
			for(MultipartFile f:file) 
				mediaClient.fileUpload(f, uploadInfoJson);
				
			return new ModelAndView("redirect:" + "https://localhost:8111/html/createAd.html");
		}catch (Exception e) {		
			return new ModelAndView("redirect:" + "https://localhost:8111/html/createAd.html");
		}
	}
	
	@PostMapping("/files-upload/message")
	public ModelAndView uploadFileMessage(@FormParam("file") MultipartFile file, @QueryParam(value = "idContent") Long idContent, @QueryParam(value = "type") ContentType type) {
		try {
			String uploadInfoJson = new ObjectMapper().writeValueAsString(new UploadInfoDTO(idContent, type));
			
			mediaClient.fileUpload(file, uploadInfoJson);
				
			return new ModelAndView("redirect:" + "https://localhost:8111/html/messages.html");
		}catch (Exception e) {		
			return new ModelAndView("redirect:" + "https://localhost:8111/html/messages.html");
		}
	}
	
	@GetMapping("/following/stories/{username}")
	public ResponseEntity<?> getFollowingStoriesByUsername(@PathVariable String username){
		try {
			Collection<ProfileDTO> profileFollowingDTOs = this.followingClient.getUnmuteFollowing(username);
			Collection<StoryDTO> storyDTOs = new ArrayList<>();
			Collection<MediaContentDTO> mediaContentDTOs= new ArrayList<>();
			
			/*Stori pratilaca*/
			for(ProfileDTO f: profileFollowingDTOs) {
				storyDTOs.addAll(storyClient.getStoriesByUsername(f.username));
			}
			/*Stori ulogovanog korisnika*/
			storyDTOs.addAll(storyClient.getStoriesByUsername(username));
			
			for(StoryDTO s: storyDTOs) {
				Collection<MediaDTO> media = this.mediaClient.getMediaById(s.id, ContentType.story);
				for(MediaDTO m: media) {
					mediaContentDTOs.add(new MediaContentDTO(m.idContent, m.contentType, m.path, s.ownerUsername));
				}
			}
			
			return new ResponseEntity<Collection<MediaContentDTO>>(mediaContentDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/following/posts/{username}")
	public ResponseEntity<?> getFollowingPostsByUsername(@PathVariable String username){
		try {
			Collection<ProfileDTO> profileFollowingDTOs = this.followingClient.getUnmuteFollowing(username);
			Collection<PostDTO> postDTOs = new ArrayList<>();
			Collection<MediaContentDTO> mediaContentDTOs= new ArrayList<>();
			
			/*Postovi pratilaca*/
			for(ProfileDTO profile: profileFollowingDTOs) { 
				postDTOs.addAll(publishingClient.getPostsByUsername(profile.username));
			}
			/*Postovi ulogovanog korisnika*/
			postDTOs.addAll(publishingClient.getPostsByUsername(username));
			
			for(PostDTO p: postDTOs) {
				Collection<MediaDTO> media = this.mediaClient.getMediaById(p.id, ContentType.post);
				for(MediaDTO m: media) {
					mediaContentDTOs.add(new MediaContentDTO(m.idContent, m.contentType, m.path, p.ownerUsername));
				}
			}
			return new ResponseEntity<Collection<MediaContentDTO>>(mediaContentDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/story/{username}")
	public ResponseEntity<?> getStoriesByUsername(@PathVariable String username){
		try {
			Collection<MediaDTO> mediaDTOs= new ArrayList<>();
			Collection<StoryDTO> storyDTOs = this.storyClient.getStoriesByUsername(username);
			for(StoryDTO p: storyDTOs) {
				Collection<MediaDTO> media = this.mediaClient.getMediaById(p.id, ContentType.story);
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
	
	@GetMapping("/post/{postId}")
	public ResponseEntity<?> getPostById(@PathVariable long postId){		
		try {			
			PostDTO postDTO = this.publishingClient.getPostById(postId);			
			Collection<MediaDTO> mediaDTOs= new ArrayList<>();
			Collection<MediaDTO> media = this.mediaClient.getMediaById(postDTO.id, ContentType.post);
			for(MediaDTO m: media) {
				mediaDTOs.add(m);
			}											
			return new ResponseEntity<PostDTO>(postDTO,  HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/posts/{username}/collection/{collectionName}")
	public ResponseEntity<?> getPostsByCollectionName(@PathVariable String collectionName, @PathVariable String username) {			
		try {
			Collection<MediaDTO> mediaDTOs= new ArrayList<>();
			Collection<FavouritePostDTO> favouritePostDTOs = this.publishingClient.getPostsByCollectionName(collectionName, username);
			for(FavouritePostDTO fp: favouritePostDTOs) {
				Collection<MediaDTO> media = this.mediaClient.getMediaById(fp.postId, ContentType.post);
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

	
	@GetMapping("/favourite-posts/{username}")
	public ResponseEntity<?> getAllFavouritePosts(@PathVariable String username) {		
		try {
			Collection<MediaDTO> mediaDTOs= new ArrayList<>();
			Collection<FavouritePostDTO> favouritePostDTOs = this.publishingClient.getAllFavouritePosts(username);
			for(FavouritePostDTO fp: favouritePostDTOs) {
				Collection<MediaDTO> media = this.mediaClient.getMediaById(fp.postId, ContentType.post);
				for(MediaDTO m: media) {
					mediaDTOs.add(m);
				}				
			}										
			return new ResponseEntity<Collection<MediaDTO>>(mediaDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
		}
	}	
	
	@GetMapping("/likes-overview/{username}")
	public ResponseEntity<?> getLikesByUsername(@PathVariable String username) {		
		try {
			Collection<MediaDTO> mediaDTOs= new ArrayList<>();
			Collection<ReactionDTO> reactionDTOs = this.activityClient.getLikesByUsername(username);
			for(ReactionDTO r: reactionDTOs) {
				Collection<MediaDTO> media = this.mediaClient.getMediaById(r.postId, ContentType.post);
				for(MediaDTO m: media) {
					mediaDTOs.add(m);
				}				
			}										
			return new ResponseEntity<Collection<MediaDTO>>(mediaDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/dislikes-overview/{username}")
	public ResponseEntity<?> getDislikesByUsername(@PathVariable String username) {		
		try {
			Collection<MediaDTO> mediaDTOs= new ArrayList<>();
			Collection<ReactionDTO> reactionDTOs = this.activityClient.getDislikesByUsername(username);
			for(ReactionDTO r: reactionDTOs) {
				Collection<MediaDTO> media = this.mediaClient.getMediaById(r.postId, ContentType.post);
				for(MediaDTO m: media) {
					mediaDTOs.add(m);
				}				
			}										
			return new ResponseEntity<Collection<MediaDTO>>(mediaDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
		}
	}	
	
		
	
	@PostMapping("/notification")
	public ResponseEntity<?> createNotification(@RequestBody NotificationDTO notificationDTO) {
		try {
			ProfileOverviewDTO profileDTO = authClient.getProfile(notificationDTO.receiverUsername);
			if ((notificationDTO.notificationType).equals("comment")) {
				if (profileDTO.allowedAllComments) {
					notificationClient.create(notificationDTO);
				}
				else if (followingClient.getActiveCommentsNotification(notificationDTO.receiverUsername, notificationDTO.wantedUsername)) {
					notificationClient.create(notificationDTO);
				}
			}
			else if ((notificationDTO.notificationType).equals("like")) {
				if (profileDTO.allowedAllLikes) {
					notificationClient.create(notificationDTO);
				}
				else if (followingClient.getActiveLikesNotification(notificationDTO.receiverUsername, notificationDTO.wantedUsername)) {
					notificationClient.create(notificationDTO);
				}				
			}
			else if (notificationDTO.notificationType.equals("message")) {
				if (profileDTO.allowedAllMessages) {
					notificationClient.create(notificationDTO);
				}
				else {
					//proveri da li receiver ima ukljuceno obavestenje za onog ko salje poruku...					
				}				
			}
			else {
				return new ResponseEntity<>("Error creating notification", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
		}
	}	
	
	@PostMapping("/notifications")
	public ResponseEntity<?> createNotifications(@RequestBody NotificationDTO notificationDTO) {
		try {			
			Collection<ProfileDTO> followersDTOs = this.followingClient.getFollowers(notificationDTO.wantedUsername);			
			if ((notificationDTO.notificationType).equals("post")) {
				for (ProfileDTO f : followersDTOs) {					
					if (this.followingClient.getActivePostNotification(f.username, notificationDTO.wantedUsername)) {
						notificationDTO.receiverUsername = f.username;
						notificationClient.create(notificationDTO);						
					}					
				}				
			}
			else if ((notificationDTO.notificationType).equals("story")) {
				for (ProfileDTO f : followersDTOs) {					
					if (this.followingClient.getActiveStoryNotification(f.username, notificationDTO.wantedUsername)) {
						notificationDTO.receiverUsername = f.username;
						notificationClient.create(notificationDTO);						
					}					
				}				
			}
			else {
				return new ResponseEntity<>("Error creating notification", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (Exception exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
		}
	}	
	
	@GetMapping("/ads/{username}")
	public ResponseEntity<?> getAdsByUsername(@PathVariable String username){
		try {
			Collection<MediaDTO> mediaDTOs= new ArrayList<>();
			Collection<CampaignDTO> campaignDTOs = this.campaignClient.getCurrentCampaignsByUsername(username);
			for(CampaignDTO c: campaignDTOs) {
				for(AdDTO a : c.ads) {
				Collection<MediaDTO> media = this.mediaClient.getMediaById(a.id, ContentType.ad);
				for(MediaDTO m: media) {
					mediaDTOs.add(m);
				}	
				}
			}
			
			return new ResponseEntity<Collection<MediaDTO>>(mediaDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/send-message")
	public ResponseEntity<?>  sendTextMessage(@RequestBody MessageDTO messageDTO){
		try {
			ProfileOverviewDTO profileOverviewDTO = authClient.getProfile(messageDTO.receiverUsername);
			messageDTO.requestType = "approved";
			
			if(!profileOverviewDTO.isPublic) {
				boolean friendship = followingClient.isFollow(messageDTO.senderUsername, messageDTO.receiverUsername);
				if(!friendship)
					messageDTO.requestType = "created";
			}
			
			return new ResponseEntity<MessageDTO>(notificationClient.sendTextMessage(messageDTO), HttpStatus.CREATED);
		}catch (Exception exception) {
			return new ResponseEntity<>("An error occurred while sending a message.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/message-content")
	public ResponseEntity<?>  checkContentInMessage(@RequestBody MessageDTO messageDTO){
		try {
			String postPath = "https://localhost:8111/html/onePost.html?id=";
			String storyPath = "https://localhost:8111/html/story.html?id=";
						
			if(messageDTO.text.contains(postPath)) {	
				Long postId = Long.parseLong(messageDTO.text.split("=")[1]);
				ProfileDTO profileDTO = publishingClient.getOwnerOfPost(postId);
				if(profileDTO.isPublic) /*Ako je profil vlasnika javan, sve je ok*/
					return new ResponseEntity<>(HttpStatus.OK);
				else { /*Ako profil vlasnika nije javan, provjeravam da li primalac poruke prati vlasnika*/
					boolean isFollow = followingClient.isFollow(messageDTO.receiverUsername, profileDTO.username);
					if(!isFollow) /*Ako ne prati, ne moze da vidi sadrzaj*/
						return new ResponseEntity<>("Content is unavailable.", HttpStatus.OK);
					else  
						return new ResponseEntity<>(HttpStatus.OK);
				}
			}else if(messageDTO.text.contains(storyPath)) {
				Long storyId = Long.parseLong(messageDTO.text.split("=")[1]);
				ProfileDTO profileDTO = publishingClient.getOwnerOfStory(storyId);
				if(profileDTO.isPublic)
					return new ResponseEntity<>(HttpStatus.OK);
				else {
					boolean isFollow = followingClient.isFollow(messageDTO.receiverUsername, profileDTO.username);
					if(!isFollow)
						return new ResponseEntity<>("Content is unavailable.", HttpStatus.OK);
					else  
						return new ResponseEntity<>(HttpStatus.OK);
				}
			}else {
				return new ResponseEntity<>("The content path is wrong.", HttpStatus.OK);
			}
			
		}catch (Exception e) {
			return new ResponseEntity<>("An error occurred while checking a message content.", HttpStatus.BAD_REQUEST);

		}
	}
	
	@GetMapping("/following/ads/{username}")
	public ResponseEntity<?> getFollowingaAdsByUsername(@PathVariable String username){
		try {
			Collection<ProfileDTO> profileFollowingDTOs = this.followingClient.getUnmuteFollowing(username);
			Collection<CampaignDTO> campaignDTOs = new ArrayList<>();
			Collection<MediaContentDTO> mediaContentDTOs= new ArrayList<>();
			
			/*Reklame pratilaca*/
			for(ProfileDTO profile: profileFollowingDTOs) { 
				campaignDTOs.addAll(this.campaignClient.getCurrentCampaignsByUsername(profile.username));
			}
			/*Reklame ulogovanog korisnika*/
			campaignDTOs.addAll(this.campaignClient.getCurrentCampaignsByUsername(username));
			
			for(CampaignDTO c: campaignDTOs) {
				for(AdDTO a : c.ads) {
				Collection<MediaDTO> media = this.mediaClient.getMediaById(a.id, ContentType.ad);
				for(MediaDTO m: media) {
					mediaContentDTOs.add(new MediaContentDTO(m.idContent, m.contentType, m.path, c.name));
				}
				}
			}
			return new ResponseEntity<Collection<MediaContentDTO>>(mediaContentDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/target-group/ads/{username}")
	public ResponseEntity<?> getTargetGroupAdsByUsername(@PathVariable String username){
		try {
			Collection<ProfileCategoryDTO> categoryDTOs = this.followingClient.findProfileCategoriesByUsername(username);
			Collection<CampaignDTO> campaignDTOs = new ArrayList<>();
			Collection<MediaContentDTO> mediaContentDTOs= new ArrayList<>();
			
			/*Reklame pratilaca*/
			for(ProfileCategoryDTO category: categoryDTOs) { 
				campaignDTOs.addAll(this.campaignClient.getCurrentCampaignsByCategory(category.name));
			}
			
			for(CampaignDTO c: campaignDTOs) {
				for(AdDTO a : c.ads) {
				Collection<MediaDTO> media = this.mediaClient.getMediaById(a.id, ContentType.ad);
				for(MediaDTO m: media) {
					mediaContentDTOs.add(new MediaContentDTO(m.idContent, m.contentType, m.path, c.name));
				}
				}
			}
			return new ResponseEntity<Collection<MediaContentDTO>>(mediaContentDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/influencer/ads/{username}")
	public ResponseEntity<?> getInfluencerAdsByUsername(@PathVariable String username){
		try {
			Collection<MediaDTO> mediaDTOs= new ArrayList<>();
			Collection<CampaignDTO> campaignDTOs = this.campaignClient.getCurrentCampaignsByInfluencerUsername(username);
			for(CampaignDTO c: campaignDTOs) {
				for(AdDTO a : c.ads) {
				Collection<MediaDTO> media = this.mediaClient.getMediaById(a.id, ContentType.ad);
				for(MediaDTO m: media) {
					mediaDTOs.add(m);
				}	
				}
			}
			
			return new ResponseEntity<Collection<MediaDTO>>(mediaDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}	
	
	
	@GetMapping("/monitoring/{agentUsername}")
	public ResponseEntity<?> getMonitoring(@PathVariable String agentUsername) {		
		try {
			Collection<MonitoringDTO> monitoringDTOs = new ArrayList<>();
			Collection<CampaignDTO> campaignDTOs = this.campaignClient.getAll();
			for (CampaignDTO c : campaignDTOs) {	
				if (c.agentUsername.equals(agentUsername)) {
					MonitoringDTO monitoringDTO = new MonitoringDTO();
					monitoringDTO.idCampaign = c.id;
					monitoringDTO.contentType = c.contentType;
					monitoringDTO.campaignType = c.campaignType;
					monitoringDTO.categoryName = c.categoryName;
					monitoringDTO.name = c.name;
					for (AdDTO a : c.ads) {
						NumberOfReactionsDTO numberOfReactionsDTO = this.activityClient.getNumberOfReactionsByAdId(a.id);
						monitoringDTO.numberLikes += numberOfReactionsDTO.numberOfLikes;
						monitoringDTO.numberDislikes += numberOfReactionsDTO.numberOfDislikes;
						monitoringDTO.numberComments += numberOfReactionsDTO.numberOfComments;
					}
					monitoringDTO.numberClicks = this.activityClient.getAllClicksByCampaignId(c.id).size();				
					monitoringDTO.numberOfClicksDTOs = this.activityClient.getNumberOfClicksByCampaignId(c.id);
					monitoringDTOs.add(monitoringDTO);
				}
			}			
			return new ResponseEntity<Collection<MonitoringDTO>>(monitoringDTOs, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	
}
