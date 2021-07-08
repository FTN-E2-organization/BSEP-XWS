package app.java.agentapp.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Agent extends User{

	private static final long serialVersionUID = 1489553854518202895L;
	
	@ColumnDefault("false")
	private boolean hasApiToken;
	
	@Size(max = 1500)
	private String token;

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
