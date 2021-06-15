package app.authservice.config;

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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import app.authservice.authentication.RestAuthenticationEntryPoint;
import app.authservice.authentication.TokenAuthenticationFilter;
import app.authservice.security.TokenUtils;
import app.authservice.service.CustomUserDetailsService;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
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
    
    @Bean
    public TokenAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        TokenAuthenticationFilter authenticationTokenFilter = new TokenAuthenticationFilter();
        authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationTokenFilter;
    }

    @Autowired
    TokenUtils tokenUtils;

    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()

			.authorizeRequests()
			
			.antMatchers("/api/auth/confirm-account").permitAll()  //aktiviranje naloga preko mejla
			
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
		web.ignoring().antMatchers(HttpMethod.POST, "/api/auth/login");
		web.ignoring().antMatchers(HttpMethod.POST, "/api/auth/verify");
		web.ignoring().antMatchers(HttpMethod.POST, "/api/auth/profile");
		web.ignoring().antMatchers(HttpMethod.POST, "/api/auth/profile/**");
		web.ignoring().antMatchers(HttpMethod.GET, "/api/auth/profile/**");

		web.ignoring().antMatchers(HttpMethod.POST, "/api/auth/password-recovery");
		web.ignoring().antMatchers(HttpMethod.POST, "/api/auth/password-change");
		web.ignoring().antMatchers(HttpMethod.POST, "/api/auth/code-check");
		web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html",
                "/**/*.css", "/**/*.js", "/**/assets/**");
	}
}
