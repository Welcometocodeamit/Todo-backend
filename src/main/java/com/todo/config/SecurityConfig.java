package com.todo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.todo.security.JwtAuthFilter;
import com.todo.security.JwtAuthEntryPoint;

@Configuration
public class SecurityConfig {
	
	@Autowired
    private JwtAuthEntryPoint point;
	
    @Autowired
    private JwtAuthFilter filter;
    
    @Autowired UserDetailsService userDetailsService;
    
    @Autowired PasswordEncoder passwordEncoder;
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf->csrf.disable());
        http.cors(cors->cors.disable());
        
        
        http.authorizeHttpRequests(req->req.requestMatchers(HttpMethod.OPTIONS, "**").permitAll()
        									.requestMatchers(HttpMethod.POST, "/user").permitAll()
        									.requestMatchers("/swagger-ui/index.html#/**").permitAll()
        									.requestMatchers("auth/login").permitAll()
        									.requestMatchers(HttpMethod.GET, "/user").authenticated()
        									.requestMatchers("/task/**").authenticated()
        									.anyRequest().permitAll());
        
        http.exceptionHandling(ex->ex.authenticationEntryPoint(point));
        
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
	
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		return authenticationProvider;
	}

}
