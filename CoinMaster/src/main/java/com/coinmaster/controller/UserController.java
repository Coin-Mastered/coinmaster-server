package com.coinmaster.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coinmaster.model.User;
import com.coinmaster.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable("id") int id) {
		return ResponseEntity.ok(userService.getById(id));
	}

	@PostMapping("/add")
	public ResponseEntity<User> addUser(@Valid @RequestBody User u) {
		return ResponseEntity.ok(userService.add(u));
	}

	@DeleteMapping("/{id}")
	public void removeUser(@PathVariable("id") int id) {
		userService.remove(id);
	}

	@PostMapping("/update")
	public ResponseEntity<User> update(@Valid @RequestBody User u) {
		return ResponseEntity.ok(userService.add(u));
	}
}
