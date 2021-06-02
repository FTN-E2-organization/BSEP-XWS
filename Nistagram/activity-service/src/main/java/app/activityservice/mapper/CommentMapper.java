package app.activityservice.mapper;

import java.util.ArrayList;
import java.util.Collection;

import app.activityservice.dto.CommentDTO;
import app.activityservice.model.Comment;
import app.activityservice.model.Profile;

public class CommentMapper {

	public static Collection<CommentDTO> toCommentDTOs(Collection<Comment> comments) {
		Collection<CommentDTO> commentDTOs = new ArrayList<>();
		for (Comment c : comments) {
			CommentDTO dto = new CommentDTO();
			dto.timestamp = c.getTimestamp();
			dto.text = c.getText();
			dto.postId = c.getPostId();
			dto.postType = c.getPostType().toString();			
			dto.ownerUsername = c.getOwner().getUsername();			
			for (Profile tagged : c.getTagged()) {
				dto.taggedUsernames.add(tagged.getUsername());
			}			
			commentDTOs.add(dto);
		}		
		return commentDTOs;
	}
	
}
