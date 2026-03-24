package com.example.demo.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.helpers.AuthPassengerDetails;
import com.example.demo.models.Passenger;
import com.example.demo.repositories.PassengerRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final PassengerRepository passengerRepository;

	public UserDetailsServiceImpl(PassengerRepository passengerRepository) {
		this.passengerRepository = passengerRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Passenger> passenger = passengerRepository.findPassengerByEmail(username);
		return passenger.map(AuthPassengerDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
	}

}
