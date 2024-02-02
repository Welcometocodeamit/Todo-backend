package com.todo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.controllers.AuthController;
import com.todo.models.JwtRequest;
import com.todo.models.JwtResponse;
import com.todo.models.User;
import com.todo.repositories.UserRepository;
import com.todo.security.JwtHelper;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;
    
    @Autowired UserRepository userRepository;


    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@org.springframework.web.bind.annotation.RequestBody JwtRequest request) {
    	System.out.println(request);
    	System.out.println(request.getUsername());

        this.doAuthenticate(request.getUsername(), request.getPassword());


        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.helper.generateToken(userDetails);
        System.out.println(token);

        JwtResponse response = new JwtResponse();
        response.setJwtToken(token);
        response.setUsername(userDetails.getUsername());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        System.out.println(authentication);
        try {
            manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }
    
    private User getUserIdByUsername(String username) {
    	User user=userRepository.findByUsername(username).orElse(null);
    	return user;
    }
    

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

}