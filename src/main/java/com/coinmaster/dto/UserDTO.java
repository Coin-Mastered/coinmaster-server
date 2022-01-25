package com.coinmaster.dto;

import java.util.Set;

import com.coinmaster.model.User;
import com.coinmaster.model.Wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private int id;

	private String firstName;

	private String lastName;

	private String email;

	private String username;
	
	private String password;

	private Set<Wallet> wallets;
	
	public User toUser() {
		return new User(id, firstName, lastName, email, username, password, wallets);
	}
}
