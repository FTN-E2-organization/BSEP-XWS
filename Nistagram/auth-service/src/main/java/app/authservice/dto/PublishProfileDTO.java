package app.authservice.dto;

import java.time.LocalDate;

import app.authservice.enums.Gender;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PublishProfileDTO {

	public String username;
	public String email;
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
	
	public PublishProfileDTO(String username, boolean isPublic,boolean isVerified, boolean allowedUnfollowerMessages, boolean allowedTagging, boolean isDeleted) {
		this.username = username;
		this.isPublic = isPublic;
		this.isVerified = isVerified;
		this.allowedUnfollowerMessages = allowedUnfollowerMessages;
		this.allowedTagging = allowedTagging;
		this.isDeleted = isDeleted;
	}

	public PublishProfileDTO(String username, String name, String email, LocalDate dateOfBirth, Gender gender,String biography, String phone, String website) {
		this.username = username;
		this.name = name;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.biography = biography;
		this.phone = phone;
		this.website = website;
	}

	public PublishProfileDTO(String username, boolean isPublic, boolean allowedUnfollowerMessages, boolean allowedTagging) {
		this.username = username;
		this.isPublic = isPublic;
		this.allowedUnfollowerMessages = allowedUnfollowerMessages;
		this.allowedTagging = allowedTagging;
	}
	
}
