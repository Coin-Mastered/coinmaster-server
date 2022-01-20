package com.coinmaster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.coinmaster.data.UserRepository;
import com.coinmaster.model.User;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Transactional(readOnly=true)
	public User getById(int id) {
		return userRepository.getById(id);
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public User add(User u) {
		System.out.println("Service pre: " + u);
		User user = userRepository.save(u);
		System.out.println("Service post: " + user);
		return user;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void remove(int id) {
		userRepository.deleteById(id);
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public User update(User u) {
		return userRepository.save(u);
	}
	
}
