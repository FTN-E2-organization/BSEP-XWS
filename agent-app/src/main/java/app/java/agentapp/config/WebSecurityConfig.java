package app.java.agentapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import app.java.agentapp.authentication.RestAuthenticationEntryPoint;
import app.java.agentapp.authentication.TokenAuthenticationFilter;
import app.java.agentapp.security.TokenUtils;
import app.java.agentapp.service.CustomUserDetailsService;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter  {
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

	@Autowired
	private CustomUserDetailsService jwtUserDetailsService;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}
   

    @Autowired
    TokenUtils tokenUtils;

    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

			.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()

			.authorizeRequests()
			
			.antMatchers("/api/auth/confirm-account").permitAll()  //aktiviranje naloga preko mejla
			
			.anyRequest().authenticated().and()
			.cors().and()

			.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService), BasicAuthenticationFilter.class);
		
		http.csrf().disable();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.POST, "/api/auth/login");
		
		web.ignoring().antMatchers(HttpMethod.GET, "/api/agent/**");
		web.ignoring().antMatchers(HttpMethod.POST, "/api/agent/**");
		web.ignoring().antMatchers(HttpMethod.PUT, "/api/agent/**");
		
		web.ignoring().antMatchers(HttpMethod.GET, "/api/customer/**");
		web.ignoring().antMatchers(HttpMethod.POST, "/api/customer/**");
		web.ignoring().antMatchers(HttpMethod.PUT, "/api/customer/**");
		
		web.ignoring().antMatchers(HttpMethod.GET, "/api/product/**");
		web.ignoring().antMatchers(HttpMethod.POST, "/api/product/**");
		web.ignoring().antMatchers(HttpMethod.PUT, "/api/product/**");
		
		web.ignoring().antMatchers(HttpMethod.GET, "/api/product-buy/**");
		web.ignoring().antMatchers(HttpMethod.POST, "/api/product-buy/**");
		web.ignoring().antMatchers(HttpMethod.PUT, "/api/product-buy/**");
		
		web.ignoring().antMatchers(HttpMethod.GET, "/api/shopping-cart/**");
		web.ignoring().antMatchers(HttpMethod.POST, "/api/shopping-cart/**");
		web.ignoring().antMatchers(HttpMethod.PUT, "/api/shopping-cart/**");
		
		web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html",
                "/**/*.css", "/**/*.js", "/**/assets/**");
	}
}
