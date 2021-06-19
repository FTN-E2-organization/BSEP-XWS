package app.authservice.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AgentRegistrationRequestDTO {

	public Long id;
	public String username;
	public boolean isApproved;
	public String email;
	public String webSite;
	public LocalDateTime timestamp;
}
