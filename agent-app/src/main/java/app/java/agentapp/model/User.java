package app.java.agentapp.model;

import javax.persistence.*;
import lombok.*;
import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

@Entity
@Table(name = "users")
@Inheritance(strategy=TABLE_PER_CLASS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
