package app.publishingservice.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import app.publishingservice.model.CustomPrincipal;
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
        String roles = httpServletRequest.getHeader("roles");
        
        String token = httpServletRequest.getHeader("Auth");

        if (roles != null && token != null) {
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();

            String[] roles_arr = roles.split("\\|");
            for (String role : roles_arr) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
            CustomPrincipal cp = new CustomPrincipal(username, roles, token);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(cp, null, authorities);
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(request, response);
    }
}
