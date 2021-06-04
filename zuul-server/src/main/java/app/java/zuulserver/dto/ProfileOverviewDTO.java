package app.java.zuulserver.dto;

import java.time.LocalDate;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ProfileOverviewDTO {
	public String username;
	public Collection<String> followers;
	public Collection<String> following;
	public String name;
	public LocalDate dateOfBirth;
	public String biography;
	public String website;
	public boolean isPublic;
	public boolean isVerified;
}
