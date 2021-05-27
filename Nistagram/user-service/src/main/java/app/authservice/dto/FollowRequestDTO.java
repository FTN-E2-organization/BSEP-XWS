package app.authservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class FollowRequestDTO {

	public Long baseProfileId;
	public Long followerProfileId;
}