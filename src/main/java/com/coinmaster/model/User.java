package com.coinmaster.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Pattern(regexp = "[a-zA-Z][a-zA-Z]*")
	private @NonNull String firstName;

	@Pattern(regexp = "[a-zA-Z][a-zA-Z]*")
	private @NonNull String lastName;

	@Email
	private @NonNull String email;

	@Column(unique=true)
	private @NonNull String username;
	
	private @NonNull String password;

	@OneToMany(mappedBy="user")
	private Set<Wallet> wallets;
}