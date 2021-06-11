package app.activityservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import app.activityservice.authentication.TokenAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public TokenAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        TokenAuthenticationFilter authenticationTokenFilter = new TokenAuthenticationFilter();
        authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationTokenFilter;
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
        http.headers().addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy", "script-src 'self'"));
		
		http
			.csrf()
	        .disable()
	        .exceptionHandling()
	        .and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests()
			.antMatchers(HttpMethod.OPTIONS).permitAll()
			.anyRequest().authenticated().and()
			.cors().and()
			.headers()
	        .xssProtection()
	        .and()
	        .contentSecurityPolicy("script-src 'self'");
		
		http.addFilterAfter(authenticationTokenFilterBean(),
                UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.GET, "/api/activity/comment/**");
		web.ignoring().antMatchers(HttpMethod.GET, "/api/activity/profile/**");
		web.ignoring().antMatchers(HttpMethod.GET, "/api/activity/reaction/**");
	}

}
