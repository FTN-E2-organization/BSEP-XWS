package app.java.agentapp.model;

import java.util.*;
import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class Permission implements GrantedAuthority {

	private static final long serialVersionUID = 3828226195423143661L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;
	
	@ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private Set<Authority> authorities = new HashSet<Authority>();

	@Override
	public String getAuthority() {
		return name;
	}
	
}
