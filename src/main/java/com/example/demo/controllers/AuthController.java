package com.example.demo.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseCookie.ResponseCookieBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AuthRequestDto;
import com.example.demo.dto.AuthResponseDto;
import com.example.demo.dto.PassengerDto;
import com.example.demo.dto.PassengerSignupRequestDto;
import com.example.demo.services.AuthService;
import com.example.demo.services.JwtService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Value("${cookie.expiry}")
	private int cookieExpiry;

	private final AuthenticationManager authenticationManager;

	private final AuthService authService;

	private final JwtService jwtService;

	public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtService jwtService) {
		this.authService = authService;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}

	@PostMapping("/signup/passenger")
	public ResponseEntity<PassengerDto> signUp(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto) {
		return new ResponseEntity<PassengerDto>(authService.signupPassenger(passengerSignupRequestDto),
				HttpStatus.CREATED);
	}

	@PostMapping("/signin/passenger")
	public ResponseEntity<?> signIn(@RequestBody AuthRequestDto authRequestDto, HttpServletRequest httpServletRequest,
			HttpServletResponse response) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword()));

		if (authentication.isAuthenticated()) {
			String jwtToken = jwtService.createToken(authRequestDto.getEmail());
			ResponseCookie cookie = ResponseCookie.from("JwtToken", jwtToken).httpOnly(true).secure(false).path("/")
					.maxAge(cookieExpiry).build();
			response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
			return new ResponseEntity<>(AuthResponseDto.builder().success(true).build(), HttpStatus.OK);
		}
		throw new UsernameNotFoundException("Username not found");
	}

	@GetMapping("/validate")
	public ResponseEntity<?> validate(HttpServletRequest request) {
		for (Cookie cookie : request.getCookies()) {
			System.out.println(cookie.getName() + " " + cookie.getValue());
		}
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}
}
