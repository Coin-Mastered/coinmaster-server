package com.coinmaster.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.coinmaster.data.UserRepository;
import com.coinmaster.data.WalletRepository;
import com.coinmaster.model.AuthRequest;
import com.coinmaster.model.ExchangeRates;
import com.coinmaster.model.LeaderboardEntry;
import com.coinmaster.model.Transaction;
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
	public User save(User u) {
		User updatedUser = userRepository.save(u);
		Set<Wallet> wallets = new HashSet<>();
		for(Wallet w : u.getWallets()) {
			w.setUser(updatedUser);
			wallets.add(walletRepository.save(w));
		}
		updatedUser.setWallets(wallets);
		return updatedUser;
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

	@Transactional(readOnly = true)
	public List<LeaderboardEntry> getLeaderboard() {
		ExchangeRates.updateExchangeRates();
		return userRepository.findAll().stream().sorted(ExchangeRates::compareUserValue).limit(5)
				.map(u -> new LeaderboardEntry(u, ExchangeRates.getUserValue(u))).collect(Collectors.toList());
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public User buy(@Valid Transaction transaction) {
		ExchangeRates.updateExchangeRates();
		Map<String, Double> rates = ExchangeRates.getCurrentExchangeRates().getRates();
		Wallet usd = walletRepository.findByUserIdAndAssetName(transaction.getUserId(), "USD");
		Wallet crypto = walletRepository.findByUserIdAndAssetName(transaction.getUserId(), transaction.getAssetName());
		if(transaction.getAmount() <= 0) { throw new IllegalArgumentException("Must be greater than zero"); }
		double transactionPrice = transaction.getAmount() / rates.get(crypto.getAssetName());
		if( transactionPrice > usd.getAmount()) { throw new RuntimeException("Insufficient funds"); }
		usd.setAmount(usd.getAmount() - transactionPrice);
		crypto.setAmount(crypto.getAmount() + transaction.getAmount());
		walletRepository.save(usd);
		walletRepository.save(crypto);
		Optional<User> u = userRepository.findById(transaction.getUserId());
		if(!u.isPresent()) { throw new RuntimeException("User cannot be found"); }
		return u.get();
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public User sell(@Valid Transaction transaction) {
		ExchangeRates.updateExchangeRates();
		Map<String, Double> rates = ExchangeRates.getCurrentExchangeRates().getRates();
		Wallet usd = walletRepository.findByUserIdAndAssetName(transaction.getUserId(), "USD");
		Wallet crypto = walletRepository.findByUserIdAndAssetName(transaction.getUserId(), transaction.getAssetName());
		if(transaction.getAmount() <= 0) { throw new IllegalArgumentException("Must be greater than zero"); }
		if(transaction.getAmount() > crypto.getAmount()) { throw new IllegalArgumentException("Insufficient funds!"); }
		double transactionPrice = transaction.getAmount() / rates.get(crypto.getAssetName());
		usd.setAmount(usd.getAmount() + transactionPrice);
		crypto.setAmount(crypto.getAmount() - transaction.getAmount());
		walletRepository.save(usd);
		walletRepository.save(crypto);
		Optional<User> u = userRepository.findById(transaction.getUserId());
		if(!u.isPresent()) { throw new RuntimeException("User cannot be found"); }
		return u.get();
	}
}
