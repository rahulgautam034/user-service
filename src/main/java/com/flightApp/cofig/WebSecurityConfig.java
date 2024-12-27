package com.flightApp.cofig;

import com.flightApp.Services.JWTUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
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
public class WebSecurityConfig {

	@Autowired
	private JWTUserDetailsService jwtUserDetailsService;


	@Bean
	public PasswordEncoder passwordEncoder() {
		log.info("passwordEncoder called");
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
		// Define which requests require authentication and authorization
		http.
				cors(Customizer.withDefaults())
				.csrf(AbstractHttpConfigurer::disable)// CSRF protection is disabled for simplicity, reconsider enabling it in production
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/api/user/authenticate","/api/user/register").permitAll()       // Public endpoints that don't require authentication
						.anyRequest().authenticated()                    // All other requests require authentication
				)
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
				.formLogin(form -> form
						.loginPage("/login")                             // Custom login page
						.permitAll()                                     // Allow anyone to access the login page
				)
				.logout(LogoutConfigurer::permitAll                                     // Allow logout without restriction
				);

		return http.build(); // Return the configured SecurityFilterChain
	}

	// AuthenticationManager for custom user details service and password encoding
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authManagerBuilder =
				http.getSharedObject(AuthenticationManagerBuilder.class);

		// Configure custom UserDetailsService and password encoder
		authManagerBuilder
				.userDetailsService(jwtUserDetailsService)
				.passwordEncoder(passwordEncoder());

		return authManagerBuilder.build(); // Build and return the AuthenticationManager
	}


}