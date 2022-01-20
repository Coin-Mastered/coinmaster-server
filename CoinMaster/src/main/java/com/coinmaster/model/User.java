package com.coinmaster.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Pattern(regexp = "[a-zA-Z][a-zA-Z]*")
	private String firstName;
	
	@Pattern(regexp = "[a-zA-Z][a-zA-Z]*")
	private String lastName;
	
	private String username;
	
	@Email
	private String email;

	@Length(min=8)
	private String password;

	public User(@Pattern(regexp = "[a-zA-Z][a-zA-Z]*") String firstName,
			@Pattern(regexp = "[a-zA-Z][a-zA-Z]*") String lastName, String username, @Email String email,
			@Length(min = 8) String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	// private List<Wallet> wallets;
}
