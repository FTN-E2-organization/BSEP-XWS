package app.authservice.dto;

import java.time.LocalDate;

import app.authservice.enums.Gender;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProfileDTO {

	public String username;
	public String email;
	public String password;
	public String name;
	public LocalDate dateOfBirth;
	public Gender gender;
	public String biography;
	public String phone;
	public String website;
	public boolean isPublic;
	public boolean isVerified;
	public boolean allowedUnfollowerMessages;
	public boolean allowedTagging;
	public boolean isDeleted;
		
}
