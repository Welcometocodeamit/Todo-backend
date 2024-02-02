package com.todo.models;

import com.todo.models.JwtResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	
	private String jwtToken;
	
	private String username;

	

}
