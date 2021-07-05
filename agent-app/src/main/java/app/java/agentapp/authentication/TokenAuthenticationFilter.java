package app.java.agentapp.authentication;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import app.java.agentapp.security.TokenUtils;

public class TokenAuthenticationFilter extends OncePerRequestFilter  {
	
	private TokenUtils tokenUtils;
	private UserDetailsService userDetailsService;

	public TokenAuthenticationFilter(TokenUtils tokenUtils, UserDetailsService userDetailsService) {
		this.tokenUtils = tokenUtils;
		this.userDetailsService = userDetailsService;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		
	    String token = tokenUtils.getToken(request);
	    
	    if (token != null) {
		    String username = tokenUtils.getUsernameFromToken(token);
			
			if (username != null) {
				
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				
				if (tokenUtils.validateToken(token, userDetails)) {
					TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
					authentication.setToken(token);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
	    
	        }
	    }
        chain.doFilter(request, response);
	}

}