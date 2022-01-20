package com.coinmaster.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonView;

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
	@Column(name = "user_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonView({ JsonViewProfiles.User.class, JsonViewProfiles.Wallet.class })
	private int id;

	@Pattern(regexp = "[a-zA-Z][a-zA-Z]*")
	private @NonNull String firstName;

	@Pattern(regexp = "[a-zA-Z][a-zA-Z]*")
	private @NonNull String lastName;

	@Email
	private @NonNull String email;

	private @NonNull String username;
	
	@Length(min=8)
	private @NonNull String password;

	@OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonView(JsonViewProfiles.User.class)
	private @NonNull Set<Wallet> wallets;
	
}