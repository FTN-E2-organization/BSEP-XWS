package app.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserTokenStateDTO {

	private String accessToken;
    private Long expiresIn;
    private String authority;
}
