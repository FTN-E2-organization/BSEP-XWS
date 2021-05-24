package app.userservice.model;

import java.util.*;
import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority {
	
	private static final long serialVersionUID = -842682929676006066L;

	@Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String name;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_permissions", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    private Set<Permission> permissions = new HashSet<Permission>();
	

	@Override
	public String getAuthority() {
		return name;
	}

}
