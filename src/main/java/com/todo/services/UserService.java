package com.todo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.todo.config.AppConfig;
import com.todo.models.User;
import com.todo.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired UserRepository userRepository;
	
	@Autowired AppConfig config;
	
//	Add user
	public ResponseEntity<?> addUser(User user){
		user.setPassword(config.passwordEncoder().encode(user.getPassword()));
		 User userq=userRepository.save(user);
		return new ResponseEntity<>(userq, HttpStatus.OK);
	}
	
//	Update user
	public ResponseEntity<?> updateUser(int id, User user){
		getUserById(id);
		user.setUserId(id);
		userRepository.save(user);
		return new ResponseEntity<>("User updated", HttpStatus.OK);
	}
	
//	delete user
	public ResponseEntity<?> deleteUser(int id){
		getUserById(id);
		userRepository.deleteById(id);
		return new ResponseEntity<>("User deleted", HttpStatus.OK);
	}
	
//	get user by id
	public ResponseEntity<?> getUserById(int id){
		User foundUser=userRepository.findById(id).orElseThrow(()->{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with given id "+id);
		});
		return new ResponseEntity<>(foundUser, HttpStatus.OK);
	}
	
//	get all users
	public ResponseEntity<?> getAllUsers(){
		List<User> allUsers=userRepository.findAll();
		return new ResponseEntity<>(allUsers, HttpStatus.OK);
	}
	
	

}
