package com.example.demo.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.example.demo.filters.JwtAuthFilter;
import com.example.demo.services.JwtService;
import com.example.demo.services.UserDetailsServiceImpl;

@Configuration
public class SpringSecurity implements WebMvcConfigurer {

	@Bean
	JwtAuthFilter jwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
		return new JwtAuthFilter(jwtService, userDetailsService);
	}

	@Bean
	UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	SecurityFilterChain filteringCriteria(HttpSecurity http, JwtAuthFilter jwtAuthFilter) {
		return http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/auth/signup/*").permitAll()
						.requestMatchers("/api/v1/auth/signin/*").permitAll())
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/auth/validate").authenticated())
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
		return daoAuthenticationProvider;
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowCredentials(true).allowedOriginPatterns("*").allowedMethods("GET", "POST",
				"PUT", "DELETE", "OPTIONS");
	}
}
