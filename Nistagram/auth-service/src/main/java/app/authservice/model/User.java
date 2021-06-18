package app.authservice.model;

import javax.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.*;
import static javax.persistence.InheritanceType.TABLE_PER_CLASS;
import java.util.*;

@Entity
@Table(name = "users")
@Inheritance(strategy=TABLE_PER_CLASS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class User implements UserDetails {
	
	private static final long serialVersionUID = 4165373733256868249L;

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
	
	@Column(unique=false, nullable=false)
	private String salt;	
	
	@Column
	@ColumnDefault("false")
	protected boolean isBlocked;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_authorities", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
	private Set<Authority> authorities = new HashSet<>();
	
}
