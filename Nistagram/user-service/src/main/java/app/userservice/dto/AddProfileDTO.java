package app.userservice.dto;

import java.time.LocalDate;
import app.userservice.enums.Gender;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class AddProfileDTO {

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
	
}
