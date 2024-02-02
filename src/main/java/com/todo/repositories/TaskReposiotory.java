package com.todo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.models.Task;
import com.todo.models.User;

@Repository
public interface TaskReposiotory extends JpaRepository<Task, Integer> {
	
	 List<Task> findByUser(User user);

}