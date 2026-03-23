package com.example.demo.services;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import com.example.demo.configurations.SpringSecurity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService implements CommandLineRunner {

	private final SpringSecurity springSecurity;
	@Value("${jwt.expiry}")
	private int expiry;

	@Value("${jwt.secret}")
	private String SECRET;

	JwtService(SpringSecurity springSecurity) {
		this.springSecurity = springSecurity;
	}

	// creates a brand new token
	private String createToken(Map<String, Object> payload, String username) {
		SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
		return Jwts.builder().claims(payload).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + expiry)).subject(username)
				.signWith(key, Jwts.SIG.HS256).compact();
	}

	@Override
	public void run(String... args) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("email", "a@b.com");
		map.put("phoneNumber", "99992973927");
		String result = createToken(map, "Govind");
		System.out.println("Generated token is: " + result);
	}
}
