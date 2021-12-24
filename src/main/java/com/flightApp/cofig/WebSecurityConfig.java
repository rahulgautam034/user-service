package com.flightApp.cofig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.extern.log4j.Log4j2;

/**
 * WebSecurityConfig for api's authorization
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Log4j2
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	private UserDetailsService jwtUserDetailsService;

	private JwtRequestFilter jwtRequestFilter;

	public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
			UserDetailsService jwtUserDetailsService, JwtRequestFilter jwtRequestFilter) {
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtUserDetailsService = jwtUserDetailsService;
		this.jwtRequestFilter = jwtRequestFilter;
	}

	/**
	 * configure AuthenticationManager so that it knows from where to load user for
	 * matching credentials Use BCryptPasswordEncoder
	 * 
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		log.info("configureGlobal called");
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	/**
	 * encode the plain text password into encrypted
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		log.info("passwordEncoder called");
		return new BCryptPasswordEncoder();
	}

	/**
	 * authenticationManagerBean
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		log.info("authenticationManagerBean called");
		return super.authenticationManagerBean();
	}

	/**
	 * configure
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		log.info("configure called");
		// We don't need CSRF for this example
		httpSecurity.csrf().disable()
				// dont authenticate this particular request
				.authorizeRequests().antMatchers("/api/user/authenticate","/api/user/register").permitAll()
				// all other requests need to be authenticated
				.anyRequest().authenticated().and().
				// make sure we use stateless session; session won't be used to
				// store user's state.
				exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		httpSecurity.cors();
	}
}