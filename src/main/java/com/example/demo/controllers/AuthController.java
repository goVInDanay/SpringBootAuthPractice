package com.example.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PassengerDto;
import com.example.demo.dto.PassengerSignupRequestDto;
import com.example.demo.services.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/signup/passenger")
	public ResponseEntity<PassengerDto> signUp(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto) {
		return new ResponseEntity<PassengerDto>(authService.signupPassenger(passengerSignupRequestDto),
				HttpStatus.CREATED);
	}

	@GetMapping("/signin/passenger")
	public ResponseEntity<?> signIn() {
		return new ResponseEntity<>(10, HttpStatus.OK);
	}
}
