package app.userservice.model;

import java.time.LocalDate;
import javax.persistence.*;
import javax.persistence.Entity;
import org.hibernate.annotations.*;
import app.userservice.enums.Gender;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
	@ColumnDefault("false")
	private boolean isVerified;
	
	@ColumnDefault("true")
	private boolean allowedUnfollowerMessages;
	
	@ColumnDefault("true")
	private boolean allowedTagging;
}
