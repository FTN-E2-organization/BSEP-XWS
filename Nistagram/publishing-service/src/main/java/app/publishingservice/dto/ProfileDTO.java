package app.publishingservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

	public String username;
	public boolean isPublic;
	public boolean allowedTagging;
	public boolean isDeleted;
	
}
