package app.java.agentapp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class Authority implements GrantedAuthority {
	
	private static final long serialVersionUID = -842682929676006066L;

	@Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String name;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "authorities_permissions", joinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    private Set<Permission> permissions = new HashSet<Permission>();
	

	@Override
	public String getAuthority() {
		return name;
	}

}
