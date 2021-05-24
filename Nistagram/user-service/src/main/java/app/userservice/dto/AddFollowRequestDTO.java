package app.userservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class AddFollowRequestDTO {

	public Long baseProfileId;
	public Long followerProfileId;
}
