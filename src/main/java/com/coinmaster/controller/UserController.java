package com.coinmaster.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coinmaster.model.AuthRequest;
import com.coinmaster.model.User;
import com.coinmaster.service.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable("id") int id) {
		return ResponseEntity.ok(userService.getById(id));
	}

	@PostMapping("/save")
	public ResponseEntity<User> saveUser(@Valid @RequestBody User u) {
		System.out.println(u);
		return ResponseEntity.ok(userService.add(u));
	}

	@DeleteMapping("/{id}")
	public void removeUser(@PathVariable("id") int id) {
		userService.remove(id);
	}
	
	@PostMapping("/login")
	public ResponseEntity<User> addUser(@Valid @RequestBody AuthRequest ar) {
		return ResponseEntity.ok(userService.login(ar));
	}
	
	@PostMapping("/check")
	public ResponseEntity<Boolean> checkUsernameExists(@Valid @RequestBody String username) {
		System.out.println(username);
		return ResponseEntity.ok(userService.checkUsernameExists(username));
	}
	
	@GetMapping("/leaderboard")
	public List<User> getLeaderboard() {
		return userService.getLeaderboard();
	}
}
