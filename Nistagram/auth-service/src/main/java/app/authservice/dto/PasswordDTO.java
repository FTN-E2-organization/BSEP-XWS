package app.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PasswordDTO {

	public String oldPassword;
	public String newPassword;
	public String username;
	
}
