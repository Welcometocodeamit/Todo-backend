package com.todo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todo.models.Task;
import com.todo.security.JwtHelper;
import com.todo.services.TaskService;
import com.todo.services.Token;

@RestController
@RequestMapping("/task")
//@CrossOrigin(origins = "task/**")
public class TaskController {
	
	@Autowired TaskService taskService;
	
	@Autowired JwtHelper helper;
	
	@Autowired Token t;
	
//	get all task
	@GetMapping("")
	public ResponseEntity<?> getTaskByUserId(){
		return taskService.getTaskByUserId(t.getUserId());
	}
	
//	add task
	@PostMapping("")
	public ResponseEntity<?> addTask(@RequestBody Task task){
		return taskService.addTask(t.getUserId(), task);
	}
	
//update task
	@PutMapping("")
	public ResponseEntity<?> updateTask(@RequestBody Task task){
		return taskService.updateTask(t.getUserId(), task);
	}
	
//	change status
	@PatchMapping("/{taskId}/{status}")
	public ResponseEntity<?> changeStatus(@PathVariable int taskId, @PathVariable String status){
		System.out.println(status);
		return taskService.changeStatus(taskId, status);
	}
	
//	delete task
	@PatchMapping("/{taskId}")
	public ResponseEntity<?> changeIsDelete(@PathVariable int taskId){
		return taskService.changeIsDelete(taskId);
	}
	
//	delete task permantly
	@DeleteMapping("")
	public ResponseEntity<?> deleteTaskPermanant(){
		return taskService.deleteTaskPermanant(t.getUserId());
	}
	
	
	

}
