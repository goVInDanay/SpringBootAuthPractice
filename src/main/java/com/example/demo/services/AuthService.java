package com.example.demo.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PassengerDto;
import com.example.demo.dto.PassengerSignupRequestDto;
import com.example.demo.repositories.PassengerRepository;
import com.example.entityservice.models.Passenger;

@Service
public class AuthService {

	PassengerRepository passengerRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public AuthService(PassengerRepository passengerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.passengerRepository = passengerRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public PassengerDto signupPassenger(PassengerSignupRequestDto passengerSignupRequestDto) {
		Passenger passenger = Passenger.builder().email(passengerSignupRequestDto.getEmail())
				.name(passengerSignupRequestDto.getName())
				.password(bCryptPasswordEncoder.encode(passengerSignupRequestDto.getPassword()))
				.phoneNumber(passengerSignupRequestDto.getPhoneNumber()).build();
		Passenger newPassenger = passengerRepository.save(passenger);

		PassengerDto response = PassengerDto.from(newPassenger);
		return response;
	}
}
