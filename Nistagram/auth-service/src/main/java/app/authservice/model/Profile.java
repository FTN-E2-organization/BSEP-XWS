package app.authservice.model;

import java.time.LocalDate;
import javax.persistence.*;
import javax.persistence.Entity;
import org.hibernate.annotations.*;

import app.authservice.enums.Gender;
import app.authservice.enums.ProfileStatus;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Profile extends User {

	private static final long serialVersionUID = -8431764590090652016L;

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

    @Column(nullable = false)
    private boolean enabled;
	
	@Column
	@ColumnDefault("false")
	private boolean isVerified;
	
	@ColumnDefault("true")
	private boolean allowedUnfollowerMessages;
	
	@ColumnDefault("true")
	private boolean allowedTagging;
	
	@Column
	@Enumerated(EnumType.STRING)
	private ProfileStatus status;

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
