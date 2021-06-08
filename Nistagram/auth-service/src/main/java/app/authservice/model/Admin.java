package app.authservice.model;

import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class Admin extends User {
	
	private static final long serialVersionUID = 5617317935031209195L;

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
		return true;
	}


}
