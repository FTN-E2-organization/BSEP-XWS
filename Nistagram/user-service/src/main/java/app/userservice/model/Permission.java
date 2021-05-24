package app.userservice.model;

import java.util.*;
import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class Permission implements GrantedAuthority {

	private static final long serialVersionUID = 3828226195423143661L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;
	
	@ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<Role>();

	@Override
	public String getAuthority() {
		return name;
	}
	
}
