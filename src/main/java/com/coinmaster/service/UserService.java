package com.coinmaster.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.coinmaster.data.UserRepository;
import com.coinmaster.data.WalletRepository;
import com.coinmaster.model.AuthRequest;
import com.coinmaster.model.ExchangeRates;
import com.coinmaster.model.User;
import com.coinmaster.model.Wallet;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	WalletRepository walletRepository;

	@Transactional(readOnly=true)
	public User getById(int id) {
		return userRepository.getById(id);
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public User add(User u) {
		System.out.println("Service pre: " + u);
		User user = userRepository.save(u);
		for(Wallet w : user.getWallets()) {
			w.setUser(user);
			walletRepository.save(w);
		}
		System.out.println("Service post: " + user);
		return user;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void remove(int id) {
		walletRepository.deleteAllByUserId(id);
		userRepository.deleteById(id);
	}
	
	@Transactional(readOnly=true)
	public User login(AuthRequest ar) {
		User user = userRepository.findByUsername(ar.getUsername());
		if(user != null && ar.getPassword().equals(user.getPassword())) {
			return user;
		}
		return null;
	}
	
	@Transactional(readOnly=true)
	public Boolean checkUsernameExists(String username) {
		return userRepository.existsByUsername(username);
	}

	@Transactional(readOnly=true)
	public List<User> getLeaderboard() {
		ExchangeRates exchangeRates = ExchangeRates.getCurrentExchangeRates();
		
		
		return null;
	}
}
