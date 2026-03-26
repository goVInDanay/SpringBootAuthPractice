package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.helpers.AuthPassengerDetails;
import com.example.demo.repositories.PassengerRepository;
import com.example.entityservice.models.Passenger;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private PassengerRepository passengerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Passenger> passenger = passengerRepository.findPassengerByEmail(username);
		return passenger.map(AuthPassengerDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
	}

}
