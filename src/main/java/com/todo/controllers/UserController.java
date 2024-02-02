package com.todo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.models.User;
import com.todo.services.UserService;

@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = "*")
public class UserController {
	
	@Autowired UserService userService;
	
//	get by id
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable int id){
		return  userService.getUserById(id);
	}
	
//	get all users
	@GetMapping("")
	public ResponseEntity<?> getAllUsers(){
		return userService.getAllUsers();
	}
	
//	add user
	@PostMapping("")
	public ResponseEntity<?> addUser(@RequestBody User user){
		return userService.addUser(user);
	}
	
//	delete user
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable int id){
		return userService.deleteUser(id);
	}
	
//	update user
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable int id){
		return userService.updateUser(id, user);	
	}

}











