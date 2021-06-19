package app.notificationservice.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import app.notificationservice.model.CustomPrincipal;
import lombok.NoArgsConstructor;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
public class TokenAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	       
	    String username = httpServletRequest.getHeader("username");
        String tokenAuthorities = httpServletRequest.getHeader("roles");
        String tokenPermissions = httpServletRequest.getHeader("permissions");
        
        String token = httpServletRequest.getHeader("Auth");
		
		if (token != null && tokenAuthorities != null && tokenPermissions != null) {
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            Set<SimpleGrantedAuthority> permissions = new HashSet<>();

            String[] roles_arr = tokenAuthorities.split("\\|");
            for (String role : roles_arr) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
            String[] permissions_arr = tokenPermissions.split("\\|");
            for (String permission : permissions_arr) {
            	permissions.add(new SimpleGrantedAuthority(permission));
            }
            
            CustomPrincipal cp = new CustomPrincipal(username, tokenAuthorities,tokenPermissions, token);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(cp, null, permissions);
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(auth);
            
        }

        chain.doFilter(request, response);
    }
}
