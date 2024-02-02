package com.todo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.todo.models.Task;
import com.todo.models.User;
import com.todo.repositories.TaskReposiotory;

@Service
public class TaskService {
	
	@Autowired User user;
	
	@Autowired TaskReposiotory taskReposiotory;
	
//	get tasks by user id
	public ResponseEntity<?> getTaskByUserId(int userid){
		user.setUserId(userid);
		List<Task> task =taskReposiotory.findByUser(user);
		if(task.size() == 0) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Task not found of user");
		}
		return new ResponseEntity<>(task, HttpStatus.OK);
	}
	
//	add task
	public ResponseEntity<?> addTask(int userId, Task task){
		user.setUserId(userId);
		task.setUser(user);
		Task savedTask=taskReposiotory.save(task);
		if(savedTask == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task not added, internal server error");
		}
		return new ResponseEntity<>("Task added", HttpStatus.OK);
	}
	
//	update task
	public ResponseEntity<?> updateTask(int userId, Task task){
		user.setUserId(userId);
		task.setUser(user);
		Task savedTask=taskReposiotory.save(task);
		if(savedTask == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task not updated, internal server error");
		}
		
		return new ResponseEntity<>("Task updated", HttpStatus.OK);
	}
	
	
//	change status of task
	public ResponseEntity<?> changeStatus(int taskId, String status){
		Task foundTask=taskReposiotory.findById(taskId).orElseThrow(()->{
			throw new ResponseStatusException(HttpStatus.OK, "Task not found in database");
		});
		
		foundTask.setStatus(status);
		taskReposiotory.save(foundTask);
		return new ResponseEntity<>("Status changed", HttpStatus.OK);
	}
	
	
//	delete task
	public ResponseEntity<?> changeIsDelete(int taskId){
		Task foundTask=taskReposiotory.findById(taskId).orElseThrow(()->{
			throw new ResponseStatusException(HttpStatus.OK, "Task not found in database");
		});
		foundTask.setDelete(true);
		taskReposiotory.save(foundTask);
		return new ResponseEntity<>("Task deleted", HttpStatus.OK);
	}
	
//	delete task permanantly
	public ResponseEntity<?> deleteTaskPermanant(int userId){
		user.setUserId(userId);
		List<Task> tasks=taskReposiotory.findByUser(user);
		tasks.forEach(task->{
			boolean delete=task.isDelete();
			if(delete==true) {
				taskReposiotory.deleteById(task.getTaskId());
			}
			
		});
		
		return new ResponseEntity<>("Task deleted permantaly", HttpStatus.OK);
	}

}
