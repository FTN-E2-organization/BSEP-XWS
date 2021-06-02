package app.activityservice.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.activityservice.dto.AddCommentDTO;
import app.activityservice.enums.PostType;
import app.activityservice.model.Comment;
import app.activityservice.repository.CommentRepository;
import app.activityservice.repository.ProfileRepository;
import app.activityservice.model.Profile;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentRepository commentRepository;
	private ProfileRepository profileRepository;
	
	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository, ProfileRepository profileRepository) {
		this.commentRepository = commentRepository;	
		this.profileRepository = profileRepository;	
	}

	@Override
	public void create(AddCommentDTO commentDTO) {
		Comment comment = new Comment();
		
		comment.setText(commentDTO.text);
		comment.setPostId(commentDTO.postId);
		comment.setPostType(PostType.valueOf(commentDTO.postType));
		comment.setOwner(profileRepository.findByUsername(commentDTO.ownerUsername));

		if (commentDTO.taggedUsernames != null && commentDTO.taggedUsernames.size() != 0) {
			Set<Profile> taggedUsernames = new HashSet<Profile>();
			for (String taggedUsername : commentDTO.taggedUsernames) {
				Profile taggedUser = profileRepository.findByUsername(taggedUsername);
				if (taggedUser.isAllowedTagging() && !taggedUser.isDeleted()) {
					taggedUsernames.add(taggedUser);
				}				
			}
			comment.setTagged(taggedUsernames);
		}		
		commentRepository.save(comment);	
	}

	@Override
	public Collection<Comment> findAllByPostId(long postId) {
		return commentRepository.findAllByPostId(postId);
	}
	
}
