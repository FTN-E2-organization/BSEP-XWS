package app.java.agentapp.model;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.*;
import static javax.persistence.InheritanceType.TABLE_PER_CLASS;
import java.util.Collection;


@Entity
@Table(name = "users")
@Inheritance(strategy=TABLE_PER_CLASS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class User implements UserDetails{

	private static final long serialVersionUID = -199098630958793988L;

	@Id
	@SequenceGenerator(name = "usersSeqGen", sequenceName = "usersSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersSeqGen")
	@Setter(AccessLevel.NONE)
	protected Long id;
	
	@Column(unique=true, nullable=false)
	protected String username;
	
	@Column(nullable=false)
	protected String email;
	
	@Column(unique=false, nullable=false)
	protected String password;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
	private Authority authority;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authority.getPermissions();
	}

	
}
