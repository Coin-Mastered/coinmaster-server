package com.coinmaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.coinmaster.data.UserRepository;
import com.coinmaster.model.AuthRequest;
import com.coinmaster.util.JwtUtil;

@RestController
public class LoginController {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/")
	public String login() {
		return "You've successfully logged in!";
	}

	@PostMapping("/authenticate")
	public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
			
		  	
		} catch (Exception ex) {
			throw new Exception("invalid username/password");
		}

		return jwtUtil.generateToken(authRequest.getUsername());
	}
	
}
