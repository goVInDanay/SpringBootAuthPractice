package com.example.demo.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import com.example.demo.services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	@Autowired
	private UserDetailsService userDetailsService;

	private final JwtService jwtService;

	private final AntPathMatcher pathMatcher = new AntPathMatcher();

	public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

//		String path = request.getServletPath();
//
//		if (path.startsWith("/api/v1/auth")) {
//			filterChain.doFilter(request, response);
//			return;
//		}

		String token = null;
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals("JwtToken")) {
					token = cookie.getValue();
				}
			}
		}

		try {
			if (token == null) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
			String email = jwtService.extractEmail(token);
			if (email != null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(email);
				if (jwtService.validateToken(token, email)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null);
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getServletPath();
		if (pathMatcher.match("/api/v1/auth/login", path) || pathMatcher.match("/api/v1/auth/register", path)) {
			return true;
		}
		return false;
	}
}
