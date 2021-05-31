package app.authservice.model;

import javax.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import lombok.*;
import static javax.persistence.InheritanceType.TABLE_PER_CLASS;
import java.util.*;

@Entity
@Table(name = "users")
@Inheritance(strategy=TABLE_PER_CLASS)
@Getter
@Setter
@NoArgsConstructor
public abstract class User {
	
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
	
	@Column
	@ColumnDefault("false")
	protected boolean isDeleted;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<Role>();
	
}