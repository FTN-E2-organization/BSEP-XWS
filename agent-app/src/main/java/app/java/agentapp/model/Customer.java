package app.java.agentapp.model;

import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class Customer extends User{

	private static final long serialVersionUID = -4270881352635777245L;

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
