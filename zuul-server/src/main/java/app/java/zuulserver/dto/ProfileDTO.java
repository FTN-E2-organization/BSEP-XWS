package app.java.zuulserver.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
	
	public String username;
	public String name;
	public LocalDate dateOfBirth;
	public String biography;
	public String website;
	public boolean isPublic;
	public boolean isVerified;
}
