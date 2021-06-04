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
			.antMatchers("/api/activity/**").permitAll()
			
			.anyRequest().authenticated().and()
			.cors();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.GET, "/api/activity/comment");
		web.ignoring().antMatchers(HttpMethod.POST, "/api/activity/comment");
		web.ignoring().antMatchers(HttpMethod.POST, "/api/activity/click");
		web.ignoring().antMatchers(HttpMethod.POST, "/api/activity/profile");
		web.ignoring().antMatchers(HttpMethod.POST, "/api/activity/reaction");
		web.ignoring().antMatchers(HttpMethod.GET, "/api/reaction/**/**");

	}
	
	
	
	
}