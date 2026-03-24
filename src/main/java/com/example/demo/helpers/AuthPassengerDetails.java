package com.example.demo.helpers;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.models.Passenger;

public class AuthPassengerDetails extends Passenger implements UserDetails {

	private String username;
	private String password;
	
	public AuthPassengerDetails(Passenger passenger) {
		this.username = passenger.getEmail();
		this.password = passenger.getPassword();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
	}

	@Override
	public String getUsername() {
		return this.username;
	}
}
