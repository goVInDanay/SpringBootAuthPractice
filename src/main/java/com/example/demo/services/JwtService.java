package com.example.demo.services;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import com.example.demo.configurations.SpringSecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService implements CommandLineRunner {
	@Value("${jwt.expiry}")
	private int expiry;

	@Value("${jwt.secret}")
	private String SECRET;

	// creates a brand new token
	public String createToken(Map<String, Object> payload, String username) {
		return Jwts.builder().claims(payload).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + expiry)).subject(username).signWith(getSignInKey())
				.compact();
	}

	public String createToken(String email) {
		return createToken(new HashMap<>(), email);
	}

	public String extractEmail(String token) {
		String claim = extractClaim(token, Claims::getSubject);
		System.out.println(claim);
		return claim;
	}

	public Object extractPayload(String token, String payloadType) {
		Claims claims = extractAllClaims(token);
		return claims.get(payloadType);
	}

	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith((SecretKey) getSignInKey()).build().parseSignedClaims(token).getPayload();
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public Key getSignInKey() {
		return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
	}

	public boolean validateToken(String token, String email) {
		final String userEmail = extractPayload(token, "email").toString();
		return userEmail.equals(userEmail) && !isTokenExpired(token);
	}

	@Override
	public void run(String... args) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("email", "a@b.com");
		map.put("phoneNumber", "99992973927");
		String result = createToken(map, "Govind");
		extractEmail(result);
		System.out.println("Generated token is: " + result);
		System.out.println(extractPayload(result, "phoneNumber").toString());
		System.out.println(validateToken(result, "govindnacharya@gmail.com"));
	}
}
