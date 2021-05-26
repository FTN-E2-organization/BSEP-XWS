package app.userservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class SearchProfileDTO {

	public Long id;
	public String username;
	public String name;
	public boolean isVerified;
	
}
