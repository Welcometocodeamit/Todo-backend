package com.todo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.todo.models.User;
import com.todo.repositories.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepository.findByUsername(username).orElseThrow(()->{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		});
		return user;
	}

}
