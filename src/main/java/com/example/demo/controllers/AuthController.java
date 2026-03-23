package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PassengerDto;
import com.example.demo.dto.PassengerSignupRequestDto;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	@PostMapping("/signup/passenger")
	public ResponseEntity<PassengerDto> signUp(@RequestBody PassengerSignupRequestDto dto) {
		return new ResponseEntity<>();
	}
}
