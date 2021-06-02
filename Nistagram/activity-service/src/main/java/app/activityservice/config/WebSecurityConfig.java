package app.activityservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

			.authorizeRequests()
			.antMatchers("/api/**").permitAll()
			
			.anyRequest().authenticated().and()
			.cors();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.GET, "/api/comment");
		web.ignoring().antMatchers(HttpMethod.POST, "/api/comment");
		web.ignoring().antMatchers(HttpMethod.POST, "/api/click");
		web.ignoring().antMatchers(HttpMethod.POST, "/api/profile");
		web.ignoring().antMatchers(HttpMethod.POST, "/api/reaction");
		web.ignoring().antMatchers(HttpMethod.GET, "/api/reaction/**/**");
	}
	
	
	
	
}
