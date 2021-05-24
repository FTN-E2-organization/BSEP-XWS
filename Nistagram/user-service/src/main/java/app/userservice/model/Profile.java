package app.userservice.model;

import java.time.LocalDate;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;

import app.userservice.enums.Gender;

@Entity
public class Profile extends User {

	@Column(nullable=false)
	private String name;
	
	@Column
	private LocalDate dateOfBirth;
	
	@Column
	private Gender gender;
	
	@Column
	private String phone;
	
	@Column
	private String biography;
	
	@Column
	private String website;
	
	@Column(nullable=false)
	private boolean isPublic;
	
	@Column
	private boolean isVerified;
	
	@Column
	@ColumnDefault("true")
	private boolean allowedUnfollowerMessages;
	
	@Column
	@ColumnDefault("true")
	private boolean allowedTagging;
}
