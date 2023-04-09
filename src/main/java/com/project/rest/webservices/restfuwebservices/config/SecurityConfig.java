package com.project.rest.webservices.restfuwebservices.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.project.rest.webservices.restfuwebservices.filtering.JwtAuthFilter;
import com.project.rest.webservices.restfuwebservices.user.LoginUserInfoUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtAuthFilter jwtFilter;
	
	@Bean
	public UserDetailsService userDetailService() {
		
		//UserDetails admin = User.withUsername("Achint").password(passwordEncoder().encode("Achint7&")).roles("ADMIN","USER").build();
		//UserDetails user = User.withUsername("user").password(passwordEncoder().encode("user")).roles("USER").build();
		
		
		
		return new LoginUserInfoUserDetailsService();
	}
	
	@Bean 
	public PasswordEncoder passwordEncoder() { 
	    return new BCryptPasswordEncoder(); 
	}
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		 return http.csrf().disable()
			.authorizeHttpRequests()
			.requestMatchers("/firstApi","/loginUser","/authenticate").permitAll()
			.anyRequest().authenticated()
			.and()
			.sessionManagement() 
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authenticationProvider(authenticationProvider())
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProv = new DaoAuthenticationProvider();
		authProv.setUserDetailsService(userDetailService());
		authProv.setPasswordEncoder(passwordEncoder());
		
		return authProv;
	}
	
	@Bean
	public AuthenticationManager authenticationmanager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

}
